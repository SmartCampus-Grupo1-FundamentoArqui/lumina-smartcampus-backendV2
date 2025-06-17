package upc.edu.pe.smartcampusbackend.course.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.course.domain.dto.AssignTeacherRequest;
import upc.edu.pe.smartcampusbackend.course.domain.dto.CourseRequest;
import upc.edu.pe.smartcampusbackend.course.domain.dto.ScheduleValidationRequest;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Course;
import upc.edu.pe.smartcampusbackend.course.domain.repositories.CourseRepository;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(CourseRequest request) {
        Course course = Course.builder()
                .name(request.getName())
                .gradeId(request.getGradeId())
                .schedule(request.getSchedule())
                .build();
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByGrade(Long gradeId) {
        return courseRepository.findByGradeId(gradeId);
    }

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8085") // URL de ScheduleService
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
}

