package upc.edu.pe.smartcampusbackend.course.application.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.course.application.services.CourseService;
import upc.edu.pe.smartcampusbackend.course.domain.dto.AssignTeacherRequest;
import upc.edu.pe.smartcampusbackend.course.domain.dto.CourseRequest;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Course;

import java.util.List;


@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/grade/{gradeId}")
    public ResponseEntity<List<Course>> getByGrade(@PathVariable Long gradeId) {
        return ResponseEntity.ok(courseService.getCoursesByGrade(gradeId));
    }

    @PutMapping("/{id}/assign-teacher")
    public ResponseEntity<Course> assignTeacher(@PathVariable Long id,
                                                @RequestBody AssignTeacherRequest request) {
        return ResponseEntity.ok(courseService.assignTeacher(id, request));
    }
}