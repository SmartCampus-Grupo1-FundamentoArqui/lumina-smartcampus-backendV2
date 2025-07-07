package upc.edu.pe.smartcampusgradebook.gradebook.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusgradebook.gradebook.application.services.GradeEntryService;
import upc.edu.pe.smartcampusgradebook.gradebook.domain.dto.GradeEntryRequest;
import upc.edu.pe.smartcampusgradebook.gradebook.domain.entities.GradeEntry;

import java.util.List;

@RestController
@RequestMapping("/gradebook/entries")
@RequiredArgsConstructor
public class GradeEntryController {

    private final GradeEntryService service;

    @PostMapping
    public ResponseEntity<GradeEntry> create(@RequestBody GradeEntryRequest request) {
        return ResponseEntity.ok(service.createOrUpdate(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeEntry> update(@PathVariable Long id, @RequestBody GradeEntryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<GradeEntry> getStudentCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return ResponseEntity.ok(service.getByStudentAndCourse(studentId, courseId));
    }

    @GetMapping("/course/{courseId}/classroom/{classroomId}")
    public ResponseEntity<List<GradeEntry>> getCourseClassroom(
            @PathVariable Long courseId,
            @PathVariable Long classroomId) {
        return ResponseEntity.ok(service.getByCourseAndClassroom(courseId, classroomId));
    }
}