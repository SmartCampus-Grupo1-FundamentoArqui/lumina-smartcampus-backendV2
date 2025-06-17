package upc.edu.pe.smartcampusbackend.teacher.domain.dto;

import lombok.Data;

@Data
public class TeacherRequest {
    private String fullName;
    private String email;
    private String phone;
}