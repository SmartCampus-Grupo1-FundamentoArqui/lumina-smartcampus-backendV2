package upc.edu.pe.smartcampusschedule.schedule.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusschedule.schedule.domain.entities.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTeacherId(Long teacherId);

    boolean existsByTeacherIdAndSchedule(Long teacherId, String schedule);
}
