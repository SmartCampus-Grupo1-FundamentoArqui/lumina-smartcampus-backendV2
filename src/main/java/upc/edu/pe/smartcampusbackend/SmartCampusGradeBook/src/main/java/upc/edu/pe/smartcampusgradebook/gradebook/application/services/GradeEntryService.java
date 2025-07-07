package upc.edu.pe.smartcampusgradebook.gradebook.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import upc.edu.pe.smartcampusgradebook.gradebook.application.infrastructure.producer.RabbitMQProducer;
import upc.edu.pe.smartcampusgradebook.gradebook.domain.dto.EmailMessage;
import upc.edu.pe.smartcampusgradebook.gradebook.domain.dto.GradeEntryRequest;
import upc.edu.pe.smartcampusgradebook.gradebook.domain.entities.GradeEntry;
import upc.edu.pe.smartcampusgradebook.gradebook.domain.repositories.GradeEntryRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GradeEntryService {

    private final GradeEntryRepository repository;
    private final RemoteValidationService remoteValidationService;
    private final RestTemplate restTemplate;
    private final RabbitMQProducer rabbitMQProducer;
    @Value("${student.service.url}")
    private  String studentServiceUrl;
    @Value("${course.service.url}")
    private  String courseServiceUrl;
    private String getStudentParentEmailUrl(Long studentId) {
        return studentServiceUrl + "/students/" + studentId + "/parent-email";
    }

    private String getCourseNameUrl(Long courseId) {
        return courseServiceUrl + "/courses/" + courseId + "/name";
    }


    public GradeEntry createOrUpdate(GradeEntryRequest request) {
        remoteValidationService.validateStudentExists(request.getStudentId());
        remoteValidationService.validateCourseExists(request.getCourseId());
        remoteValidationService.validateClassroomExists(request.getClassroomId());
        remoteValidationService.validateStudentInClassroom(request.getStudentId(), request.getClassroomId());
        remoteValidationService.validateCourseBelongsToTeacher(request.getCourseId(), request.getTeacherId());

        GradeEntry entry = repository.findByStudentIdAndCourseIdAndClassroomId(
                request.getStudentId(), request.getCourseId(), request.getClassroomId()
        ).orElse(new GradeEntry());

        entry.setStudentId(request.getStudentId());
        entry.setCourseId(request.getCourseId());
        entry.setClassroomId(request.getClassroomId());
        entry.setTeacherId(request.getTeacherId());

        entry.setNota1(request.getNota1());
        entry.setNota2(request.getNota2());
        entry.setNota3(request.getNota3());
        entry.setNota4(request.getNota4());
        entry.setNota5(request.getNota5());
        entry.setNota6(request.getNota6());
        entry.setNota7(request.getNota7());

        entry.recalculate();
        GradeEntry saved = repository.save(entry);
        // Enviar correo si está desaprobado
        if (saved.getAverage() != null && saved.getAverage() < 11) {
            checkAndNotifyDesaproved(saved.getStudentId(), saved.getCourseId());
        }
        return saved;
    }

    public GradeEntry update(Long id, GradeEntryRequest request) {
        GradeEntry entry = repository.findById(id).orElseThrow();
        entry.setNota1(request.getNota1());
        entry.setNota2(request.getNota2());
        entry.setNota3(request.getNota3());
        entry.setNota4(request.getNota4());
        entry.setNota5(request.getNota5());
        entry.setNota6(request.getNota6());
        entry.setNota7(request.getNota7());

        entry.recalculate();
        GradeEntry saved = repository.save(entry);
        // Enviar correo si está desaprobado
        if (saved.getAverage() != null && saved.getAverage() < 11) {
            checkAndNotifyDesaproved(saved.getStudentId(), saved.getCourseId());
        }
        return saved;
    }


    private String getCourseName(Long courseId) {
        ResponseEntity<String> response = restTemplate.getForEntity(getCourseNameUrl(courseId), String.class, courseId);
        return response.getBody();
    }

    private void checkAndNotifyDesaproved(Long studentId, Long courseId) {
        // Obtener el correo del apoderado del estudiante
        String courseName = getCourseName(courseId);
        ResponseEntity<String> response = restTemplate.getForEntity(getStudentParentEmailUrl(studentId), String.class, studentId);
        String parentEmail = response.getBody();

        EmailMessage message = new EmailMessage();
        message.setParentEmail(parentEmail);
        message.setSubject("Notificación de desaprobación en curso");
        message.setBody("El estudiante ha desaprobado el curso: " + courseName);
        rabbitMQProducer.sendEmailMessage(message);

    }

    public GradeEntry getByStudentAndCourse(Long studentId, Long courseId) {
        return repository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Grades not found"));
    }

    public List<GradeEntry> getByCourseAndClassroom(Long courseId, Long classroomId) {
        return repository.findByCourseIdAndClassroomId(courseId, classroomId);
    }

}





















