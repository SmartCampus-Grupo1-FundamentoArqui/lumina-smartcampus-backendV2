package upc.edu.pe.smartcampusschedule.schedule.domain.dto;

import lombok.Data;

@Data
public class ScheduleValidationRequest {
    private Long teacherId;
    private String schedule;  // formato simple: "Mon-Wed 08:00-09:30"
}
