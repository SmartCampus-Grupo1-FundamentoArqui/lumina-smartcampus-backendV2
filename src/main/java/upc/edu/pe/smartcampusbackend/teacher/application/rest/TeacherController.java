package upc.edu.pe.smartcampusbackend.teacher.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.teacher.application.services.TeacherService;
import upc.edu.pe.smartcampusbackend.teacher.domain.dto.TeacherRequest;
import upc.edu.pe.smartcampusbackend.teacher.domain.entities.Teacher;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody TeacherRequest request) {
        return ResponseEntity.ok(teacherService.createTeacher(request));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }
}
