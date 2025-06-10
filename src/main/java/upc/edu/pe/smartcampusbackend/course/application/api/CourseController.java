package upc.edu.pe.smartcampusbackend.course.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Course;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Grade;
import upc.edu.pe.smartcampusbackend.course.application.services.CourseService;
import java.util.List;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Endpoint para crear un nuevo grado
    @PostMapping("/grades")
    public Grade createGrade(@RequestBody Grade grade) {
        return courseService.createGrade(grade);
    }

    // Endpoint para crear un nuevo curso
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    // Endpoint para obtener todos los grados
    @GetMapping("/grades")
    public List<Grade> getAllGrades() {
        return courseService.getAllGrades();
    }

    // Endpoint para obtener todos los cursos
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Endpoint para asignar un curso a un grado (puedes modificar según tu lógica de negocio)
    @PostMapping("/assign")
    public Course assignCourseToGrade(@RequestParam Long gradeId, @RequestParam Long courseId) {
        return courseService.assignCourseToGrade(gradeId, courseId);
    }
}