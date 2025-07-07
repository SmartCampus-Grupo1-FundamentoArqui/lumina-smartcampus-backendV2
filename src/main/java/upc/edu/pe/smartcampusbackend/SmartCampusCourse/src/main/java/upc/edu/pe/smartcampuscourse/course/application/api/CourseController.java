package upc.edu.pe.smartcampuscourse.course.application.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampuscourse.course.application.services.CourseService;
import upc.edu.pe.smartcampuscourse.course.domain.dto.AssignTeacherRequest;
import upc.edu.pe.smartcampuscourse.course.domain.dto.CourseRequest;
import upc.edu.pe.smartcampuscourse.course.domain.entities.Course;


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

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<Course>> getByClassroom(@PathVariable Long classroomId) {
        return ResponseEntity.ok(courseService.getCoursesByClassroom(classroomId));
    }

    @GetMapping("/{courseId}/teacher/{teacherId}")
    public ResponseEntity<Course> getByCourseAndTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.getCourseByCourseAndTeacher(courseId, teacherId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.getCoursesByTeacher(teacherId));
    }

    @GetMapping("/{courseId}/name")
    public ResponseEntity<String> getCourseName(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseName(courseId));
    }

    @PutMapping("/{id}/assign-teacher")
    public ResponseEntity<Course> assignTeacher(@PathVariable Long id,
                                                @RequestBody AssignTeacherRequest request) {
        return ResponseEntity.ok(courseService.assignTeacher(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}