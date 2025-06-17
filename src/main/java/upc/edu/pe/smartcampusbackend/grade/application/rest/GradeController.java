package upc.edu.pe.smartcampusbackend.grade.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.grade.application.services.GradeService;
import upc.edu.pe.smartcampusbackend.grade.domain.dto.GradeRequest;
import upc.edu.pe.smartcampusbackend.grade.domain.entities.Grade;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<Grade> create(@RequestBody GradeRequest request) {
        return ResponseEntity.ok(gradeService.createGrade(request));
    }

    @GetMapping
    public ResponseEntity<List<Grade>> getAll() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gradeService.getGradeById(id));
    }
}