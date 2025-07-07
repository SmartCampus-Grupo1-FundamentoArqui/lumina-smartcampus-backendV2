package upc.edu.pe.smartcampusattendance.attendance.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.AttendanceSession;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    boolean existsByCourseIdAndClassroomIdAndDate(Long courseId, Long classroomId, LocalDate date);
    List<AttendanceSession> findByTeacherId(Long teacherId);
    List<AttendanceSession> findByCourseIdAndWeekOfYear(Long courseId, Integer weekOfYear);
}
