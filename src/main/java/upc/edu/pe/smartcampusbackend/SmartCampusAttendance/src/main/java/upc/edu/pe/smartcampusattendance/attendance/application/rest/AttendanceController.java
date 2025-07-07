package upc.edu.pe.smartcampusattendance.attendance.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusattendance.attendance.application.services.AttendanceService;
import upc.edu.pe.smartcampusattendance.attendance.domain.dto.AttendanceSessionRequest;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.AttendanceSession;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.StudentAttendance;

import java.util.List;

@RestController
@RequestMapping("/attendance/sessions")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;

    @PostMapping
    public ResponseEntity<AttendanceSession> create(@RequestBody AttendanceSessionRequest request) {
        return ResponseEntity.ok(service.createSession(request));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AttendanceSession>> byTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(service.getSessionsByTeacher(teacherId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceSession> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/student-attendance/{id}")
    public ResponseEntity<StudentAttendance> updateStudentAttendance(
            @PathVariable Long id,
            @RequestBody StudentAttendance updatedAttendance) {
        return ResponseEntity.ok(service.updateStudentAttendance(id, updatedAttendance));
    }

    @GetMapping("/history")
    public ResponseEntity<List<AttendanceSession>> byCourseAndWeek(
            @RequestParam Long courseId,
            @RequestParam Integer week) {
        return ResponseEntity.ok(service.getByCourseAndWeek(courseId, week));
    }
}
