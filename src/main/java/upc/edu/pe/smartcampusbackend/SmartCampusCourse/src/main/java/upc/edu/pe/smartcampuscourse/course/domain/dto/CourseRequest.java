package upc.edu.pe.smartcampuscourse.course.domain.dto;

import lombok.Data;

@Data
public class CourseRequest {
    private String name;
    private Long classroomId;
    private String schedule;
    private String imageUrl;
}