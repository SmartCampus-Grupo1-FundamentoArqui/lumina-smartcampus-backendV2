package upc.edu.pe.smartcampusgradebook.gradebook.domain.dto;

import lombok.Data;

@Data
public class GradeEntryRequest {
    private Long studentId;
    private Long courseId;
    private Long classroomId;
    private Long teacherId;

    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double nota4;
    private Double nota5;
    private Double nota6;
    private Double nota7;
}