package upc.edu.pe.smartcampusbackend.teacher.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.teacher.domain.entities.Teacher;
import upc.edu.pe.smartcampusbackend.teacher.application.services.TeacherService;


@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Endpoint para crear un nuevo profesor
    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    // Endpoint para asignar un profesor a un curso
    @PostMapping("/assign")
    public String assignTeacherToCourse(@RequestParam Long teacherId, @RequestParam Long courseId) {
        boolean success = teacherService.assignTeacherToCourse(teacherId, courseId);
        return success ? "Teacher assigned to course successfully" : "Error assigning teacher to course";
    }
}
