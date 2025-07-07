package upc.edu.pe.smartcampusattendance.attendance.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import upc.edu.pe.smartcampusattendance.attendance.application.infrastructure.producer.RabbitMQProducer;
import upc.edu.pe.smartcampusattendance.attendance.domain.dto.AttendanceSessionRequest;
import upc.edu.pe.smartcampusattendance.attendance.domain.dto.EmailMessage;
import upc.edu.pe.smartcampusattendance.attendance.domain.dto.StudentAttendanceDTO;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.AttendanceSession;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.AttendanceStatus;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.StudentAttendance;
import upc.edu.pe.smartcampusattendance.attendance.domain.repositories.AttendanceSessionRepository;
import upc.edu.pe.smartcampusattendance.attendance.domain.repositories.StudentAttendanceRepository;
import upc.edu.pe.smartcampusattendance.attendance.application.services.RemoteValidationService;

import java.time.DayOfWeek;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceSessionRepository sessionRepo;
    private final StudentAttendanceRepository studentRepo;
    private final RestTemplate restTemplate;
    private final RemoteValidationService remoteValidationService;
    private final RabbitMQProducer rabbitMQProducer;

    @Value("${student.service.url}")
    private String studentServiceUrl;

    private String getStudentParentEmailUrl(Long studentId) {
        return studentServiceUrl + "/students/" + studentId + "/parent-email";
    }


    public AttendanceSession createSession(AttendanceSessionRequest request) {
        remoteValidationService.validateCourseExists(request.getCourseId());
        remoteValidationService.validateClassroomExists(request.getClassroomId());
        remoteValidationService.validateTeacherExists(request.getTeacherId());
        remoteValidationService.validateStudentsExist(request.getAttendances()
                .stream()
                .map(StudentAttendanceDTO::getStudentId)
                .toList());
        remoteValidationService.validateStudentInClassroom(request.getAttendances()
                .stream()
                .map(StudentAttendanceDTO::getStudentId)
                .toList(), request.getClassroomId());

        remoteValidationService.validateCourseBelongsToTeacher(request.getCourseId(), request.getTeacherId());


        // Validar existencia de entidades relacionadas
        if (request.getCourseId() == null || request.getClassroomId() == null || request.getTeacherId() == null) {
            throw new IllegalArgumentException("Course, Classroom and Teacher IDs must be provided");
        }


        // Validar que la fecha esté entre lunes y viernes
        DayOfWeek dow = request.getDate().getDayOfWeek();
        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Attendance only allowed Monday to Friday");
        }

        // Evitar duplicados
        boolean exists = sessionRepo.existsByCourseIdAndClassroomIdAndDate(
                request.getCourseId(), request.getClassroomId(), request.getDate());

        if (exists) {
            throw new RuntimeException("Attendance already registered for that course and day.");
        }

        AttendanceSession session = AttendanceSession.builder()
                .courseId(request.getCourseId())
                .classroomId(request.getClassroomId())
                .teacherId(request.getTeacherId())
                .date(request.getDate())
                .dayOfWeek(dow.name())
                .weekOfYear(Year.now().atDay(request.getDate().getDayOfYear()).get(WeekFields.ISO.weekOfWeekBasedYear()))
                .build();

        List<StudentAttendance> studentAttendances = request.getAttendances()
                .stream()
                .map(dto -> StudentAttendance.builder()
                        .studentId(dto.getStudentId())
                        .status(dto.getStatus())
                        .session(session)
                        .build())
                .toList();

        session.setStudentAttendances(studentAttendances);

        AttendanceSession savedSession = sessionRepo.save(session);

        // Verificar inasistencias para cada estudiante al crear la sesión
        for (StudentAttendance sa : savedSession.getStudentAttendances()) {
            if (sa.getStatus() == AttendanceStatus.ABSENT) {
                checkAndNotifyAbsences(sa.getStudentId(), savedSession.getClassroomId());
            }
        }

        return savedSession;
    }


    public List<AttendanceSession> getSessionsByTeacher(Long teacherId) {
        return sessionRepo.findByTeacherId(teacherId);
    }

    public Optional<AttendanceSession> getById(Long id) {
        return sessionRepo.findById(id);
    }

    public List<AttendanceSession> getByCourseAndWeek(Long courseId, Integer weekOfYear) {
        return sessionRepo.findByCourseIdAndWeekOfYear(courseId, weekOfYear);
    }

    public StudentAttendance updateStudentAttendance(Long id, StudentAttendance updatedAttendance) {
        StudentAttendance attendance = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setStatus(updatedAttendance.getStatus());
        StudentAttendance savedAttendance = studentRepo.save(attendance);

        // Llamar a la verificación de inasistencias solo si el estado es ABSENT
        if (savedAttendance.getStatus() == AttendanceStatus.ABSENT) {
            checkAndNotifyAbsences(savedAttendance.getStudentId(), savedAttendance.getSession().getClassroomId());
        }

        return savedAttendance;
    }


    private void checkAndNotifyAbsences(Long studentId, Long session_classroomId) {
        // Contar las ausencias del estudiante en la sesión actual
        long absencesCount = studentRepo.countByStudentIdAndSessionClassroomIdAndStatus(
                studentId, session_classroomId, AttendanceStatus.ABSENT);

        if (absencesCount >= 7) {
            ResponseEntity<String> response = restTemplate.getForEntity(getStudentParentEmailUrl(studentId), String.class, studentId);
            String parentEmail = response.getBody();

            EmailMessage message = new EmailMessage();
            message.setParentEmail(parentEmail);
            message.setSubject("Alerta de inasistencias");
            message.setBody("Estimado padre, Su hijo/a ha acumulado 7 o más inasistencias. Por favor, comuníquese con la institución.");
            rabbitMQProducer.sendEmailMessage(message);
        }
    }
}