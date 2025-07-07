package upc.edu.pe.smartcampuscourse.course.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import upc.edu.pe.smartcampuscourse.course.domain.dto.AssignTeacherRequest;
import upc.edu.pe.smartcampuscourse.course.domain.dto.CourseRequest;
import upc.edu.pe.smartcampuscourse.course.domain.dto.ScheduleValidationRequest;
import upc.edu.pe.smartcampuscourse.course.domain.entities.Course;
import upc.edu.pe.smartcampuscourse.course.domain.repositories.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(CourseRequest request) {
        Course course = Course.builder()
                .name(request.getName())
                .classroomId(request.getClassroomId())
                .schedule(request.getSchedule())
                .imageUrl(request.getImageUrl())
                .build();
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setName(request.getName());
        course.setSchedule(request.getSchedule());
        course.setImageUrl(request.getImageUrl());

        return courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    public Course getCourseByCourseAndTeacher(Long courseId, Long teacherId) {
        Course course = courseRepository.findByIdAndTeacherId(courseId, teacherId);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not assigned to this teacher");
        }
        return course;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByClassroom(Long classroomId) {
        return courseRepository.findByClassroomId(classroomId);
    }

    public String getCourseName(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getName();
    }

    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        // Usa variable de entorno para la URL base del ScheduleService
        String scheduleServiceBaseUrl = System.getenv().getOrDefault("SCHEDULE_SERVICE_BASE_URL", "http://localhost:8085");
        this.webClient = WebClient.builder()
                .baseUrl(scheduleServiceBaseUrl)
                .build();
    }


    public Course assignTeacher(Long courseId, AssignTeacherRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Verificar si el horario ya está ocupado para ese profesor
        Boolean available = webClient.post()
                .uri("/schedule/validate")
                .bodyValue(new ScheduleValidationRequest(request.getTeacherId(), course.getSchedule()))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(available)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Teacher already has a course at this time.");
        }

        // Asignar el profesor
        course.setTeacherId(request.getTeacherId());
        Course saved = courseRepository.save(course);

        // Registrar la carga docente en el ScheduleService
        webClient.post()
                .uri("/schedule")
                .bodyValue(new ScheduleValidationRequest(request.getTeacherId(), course.getSchedule()))
                .retrieve()
                .toBodilessEntity()
                .block();

        return saved;
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        // Verificar si el curso tiene un profesor asignado
        if (course.getTeacherId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete course with assigned teacher.");
        }
        courseRepository.delete(course);
    }
}
