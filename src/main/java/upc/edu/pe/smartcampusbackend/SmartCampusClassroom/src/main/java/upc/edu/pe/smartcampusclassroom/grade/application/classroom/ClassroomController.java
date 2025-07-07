package upc.edu.pe.smartcampusclassroom.grade.application.classroom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusclassroom.grade.application.services.ClassroomService;
import upc.edu.pe.smartcampusclassroom.grade.domain.dto.ClassroomRequest;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Classroom;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<Classroom> create(@RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(classroomService.createClassroom(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> update(@PathVariable Long id, @RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, request));
    }

    @GetMapping
    public ResponseEntity<List<Classroom>> getAll() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping("/{id}/has-space")
    public ResponseEntity<Boolean> hasSpace(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.hasSpace(id));
    }

    @PutMapping("/{id}/increment")
    public ResponseEntity<Void> incrementStudentCount(@PathVariable Long id) {
        classroomService.incrementStudentCount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/decrement")
    public ResponseEntity<Void> decrementStudentCount(@PathVariable Long id) {
        classroomService.decrementStudentCount(id);
        return ResponseEntity.ok().build();
    }


}
