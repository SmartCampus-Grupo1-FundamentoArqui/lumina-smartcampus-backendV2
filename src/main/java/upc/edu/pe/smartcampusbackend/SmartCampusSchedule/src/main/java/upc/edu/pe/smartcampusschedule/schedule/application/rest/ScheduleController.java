package upc.edu.pe.smartcampusschedule.schedule.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusschedule.schedule.application.services.ScheduleService;
import upc.edu.pe.smartcampusschedule.schedule.domain.dto.ScheduleValidationRequest;
import upc.edu.pe.smartcampusschedule.schedule.domain.entities.Schedule;


import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody ScheduleValidationRequest request) {
        boolean available = service.isScheduleAvailable(request);
        return ResponseEntity.ok(available);
    }

    @PostMapping
    public ResponseEntity<Schedule> add(@RequestBody ScheduleValidationRequest request) {
        return ResponseEntity.ok(service.addSchedule(request));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Schedule>> getTeacherSchedule(@PathVariable Long teacherId) {
        return ResponseEntity.ok(service.getByTeacher(teacherId));
    }

    @GetMapping("/teacher/{teacherId}/count")
    public ResponseEntity<Integer> getTeacherLoad(@PathVariable Long teacherId) {
        return ResponseEntity.ok(service.countByTeacher(teacherId));
    }
}
