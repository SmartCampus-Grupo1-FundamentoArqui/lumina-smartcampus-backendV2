package upc.edu.pe.smartcampusbackend.course.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleValidationRequest {
    private Long teacherId;
    private String schedule;
}