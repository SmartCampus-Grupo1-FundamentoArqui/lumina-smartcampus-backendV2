package upc.edu.pe.smartcampusgradebook.gradebook.application.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


@Service
public class RemoteValidationService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${student.service.url}")
    private String studentServiceUrl;

    @Value("${course.service.url}")
    private String courseServiceUrl;

    @Value("${classroom.service.url}")
    private String classroomServiceUrl;

    public void validateStudentExists(Long studentId) {
        try {
            restTemplate.getForObject(studentServiceUrl+"/students/" + studentId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Student not found");
        }
    }

    public void validateCourseExists(Long courseId) {
        try {
            restTemplate.getForObject( courseServiceUrl+"/courses/" + courseId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Course not found");
        }
    }

    public void validateClassroomExists(Long classroomId) {
        try {
            restTemplate.getForObject(classroomServiceUrl+"/classrooms/" + classroomId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Classroom not found");
        }
    }

    public void validateStudentInClassroom(Long studentId, Long classroomId) {
        try {
            restTemplate.getForObject(studentServiceUrl+"/students/" + studentId + "/classroom/" + classroomId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("The student does not belong to the specified classroom");
        }
    }

    public void validateCourseBelongsToTeacher(Long courseId, Long teacherId) {
        try {
            restTemplate.getForObject(courseServiceUrl+"/courses/" + courseId + "/teacher/" + teacherId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("The course does not belong to the specified teacher");
        }
    }
}

