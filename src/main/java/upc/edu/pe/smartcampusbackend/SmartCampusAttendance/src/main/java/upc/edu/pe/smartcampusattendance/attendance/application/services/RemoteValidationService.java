package upc.edu.pe.smartcampusattendance.attendance.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RemoteValidationService {
    private final RestTemplate restTemplate;

    public RemoteValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${student.service.url}")
    private String studentServiceUrl;

    @Value("${course.service.url}")
    private String courseServiceUrl;

    @Value("${classroom.service.url}")
    private String classroomServiceUrl;

    @Value("${teacher.service.url}")
    private String teacherServiceUrl;

    public void validateStudentsExist(List<Long> studentIds) {
        for (Long studentId : studentIds) {
            try {
                restTemplate.getForObject(studentServiceUrl+"/students/" + studentId, Object.class);
            } catch (HttpClientErrorException.NotFound e) {
                throw new RuntimeException("Student not found: " + studentId);
            }
        }
    }

    public void validateCourseExists(Long courseId) {
        try {
            restTemplate.getForObject(courseServiceUrl+"/courses/" + courseId, Object.class);
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

    public void validateTeacherExists(Long teacherId) {
        try {
            restTemplate.getForObject(teacherServiceUrl+"/teachers/" + teacherId, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Teacher not found");
        }
    }

    public void validateStudentInClassroom(List<Long> studentIds, Long classroomId) {
        for (Long studentId : studentIds) {
            try {
                restTemplate.getForObject(studentServiceUrl+"/students/" + studentId + "/classroom/" + classroomId, Object.class);
            } catch (HttpClientErrorException.NotFound e) {
                throw new RuntimeException("The student does not belong to the specified classroom: " + studentId);
            }
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
