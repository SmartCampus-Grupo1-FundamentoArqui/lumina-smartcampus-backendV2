package upc.edu.pe.smartcampusbackend.course.domain.dto;

import lombok.Data;

@Data
public class CourseRequest {
    private String name;
    private Long gradeId;
    private String schedule;
}