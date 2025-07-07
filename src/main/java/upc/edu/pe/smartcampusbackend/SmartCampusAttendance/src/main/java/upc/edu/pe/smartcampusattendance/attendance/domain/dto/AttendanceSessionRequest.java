package upc.edu.pe.smartcampusattendance.attendance.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AttendanceSessionRequest {
    private Long courseId;
    private Long classroomId;
    private Long teacherId;
    private LocalDate date;
    private List<StudentAttendanceDTO> attendances;
}
