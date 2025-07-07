package upc.edu.pe.smartcampusattendance.attendance.domain.dto;

import lombok.Data;
import upc.edu.pe.smartcampusattendance.attendance.domain.entities.AttendanceStatus;

@Data
public class StudentAttendanceDTO {
    private Long studentId;
    private AttendanceStatus status;
}
