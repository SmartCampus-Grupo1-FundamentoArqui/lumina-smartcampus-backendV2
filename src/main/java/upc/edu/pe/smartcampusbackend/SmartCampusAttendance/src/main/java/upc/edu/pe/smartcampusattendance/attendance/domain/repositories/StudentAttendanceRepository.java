package upc.edu.pe.smartcampusattendance.attendance.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.AttendanceStatus;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.StudentAttendance;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
    long countByStudentIdAndSessionClassroomIdAndStatus(Long studentId, Long session_classroomId, AttendanceStatus status);
}
