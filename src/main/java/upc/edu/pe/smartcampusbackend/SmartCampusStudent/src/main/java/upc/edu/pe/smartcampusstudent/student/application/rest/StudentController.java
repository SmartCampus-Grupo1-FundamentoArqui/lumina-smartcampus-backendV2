package upc.edu.pe.smartcampusstudent.student.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusstudent.student.application.services.StudentService;
import upc.edu.pe.smartcampusstudent.student.domain.dto.AssignClassroomRequest;
import upc.edu.pe.smartcampusstudent.student.domain.dto.StudentRequest;
import upc.edu.pe.smartcampusstudent.student.domain.entities.Student;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody StudentRequest request) {
        return ResponseEntity.ok(service.registerStudent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody StudentRequest request) {
        return ResponseEntity.ok(service.updateStudent(id, request));
    }

    @GetMapping
    public ResponseEntity<List<Student>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<Student>> getByClassroom(@PathVariable Long classroomId) {
        return ResponseEntity.ok(service.getByClassroom(classroomId));
    }

    @GetMapping("/{studentId}/classroom/{classroomId}")
    public ResponseEntity<Student> getByStudentAndClassroom(@PathVariable Long studentId, @PathVariable Long classroomId) {
        return ResponseEntity.ok(service.getByStudentAndClassroom(studentId, classroomId));
    }

    @GetMapping("/{studentId}/parent-email")
    public ResponseEntity<String> getParentEmail(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getParentEmail(studentId));
    }

    @PutMapping("/{id}/assign-classroom")
    public ResponseEntity<Student> assignClassroom(@PathVariable Long id, @RequestBody AssignClassroomRequest request) {
        return ResponseEntity.ok(service.assignClassroom(id, request.getClassroomId()));
    }

}