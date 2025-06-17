package upc.edu.pe.smartcampusbackend.schedule.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.schedule.domain.dto.ScheduleValidationRequest;
import upc.edu.pe.smartcampusbackend.schedule.domain.entities.Schedule;
import upc.edu.pe.smartcampusbackend.schedule.domain.repositories.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;

    public boolean isScheduleAvailable(ScheduleValidationRequest request) {
        return !repository.existsByTeacherIdAndSchedule(request.getTeacherId(), request.getSchedule());
    }

    public Schedule addSchedule(ScheduleValidationRequest request) {
        Schedule schedule = Schedule.builder()
                .teacherId(request.getTeacherId())
                .schedule(request.getSchedule())
                .build();
        return repository.save(schedule);
    }

    public List<Schedule> getByTeacher(Long teacherId) {
        return repository.findByTeacherId(teacherId);
    }

    public int countByTeacher(Long teacherId) {
        return repository.findByTeacherId(teacherId).size();
    }
}
