package upc.edu.pe.smartcampusclassroom.grade.domain.dto;

import lombok.Data;

@Data
public class ClassroomRequest {
    private Long gradeId;
    private String section;
    private String roomNumber;
    private String imageUrl;
    private int capacity;

}