package upc.edu.pe.smartcampususer.teacher.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampususer.teacher.application.services.TeacherService;
import upc.edu.pe.smartcampususer.teacher.domain.dto.EmailMessage;
import upc.edu.pe.smartcampususer.teacher.domain.dto.TeacherMinimalResponse;
import upc.edu.pe.smartcampususer.teacher.domain.dto.TeacherRequest;
import upc.edu.pe.smartcampususer.teacher.domain.entities.Teacher;
import upc.edu.pe.smartcampususer.teacher.application.infrastructure.producer.RabbitMQProducer;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody TeacherRequest request) {
        return ResponseEntity.ok(teacherService.createTeacher(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody TeacherRequest request) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TeacherMinimalResponse> getByEmail(@PathVariable String email) {
        Teacher teacher = teacherService.findByEmail(email);
        TeacherMinimalResponse response = new TeacherMinimalResponse();
        response.setEmail(teacher.getEmail());
        response.setPassword(teacher.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/notify-parent")
    public ResponseEntity<String> notifyParent(@RequestBody EmailMessage message) {
        teacherService.notifyParent(message.getParentEmail(), message.getSubject(), message.getBody());
        return ResponseEntity.ok("Correo enviado (si RabbitMQ y el consumidor están funcionando)");
    }
}
