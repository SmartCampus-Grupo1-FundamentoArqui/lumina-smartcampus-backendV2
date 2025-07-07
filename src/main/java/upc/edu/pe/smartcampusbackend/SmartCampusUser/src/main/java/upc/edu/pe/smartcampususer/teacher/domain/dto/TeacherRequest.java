package upc.edu.pe.smartcampususer.teacher.domain.dto;

import lombok.Data;

@Data
public class TeacherRequest {
    private String firstName;
    private String lastNameFather;
    private String lastNameMother;
    private String email;
    private String phone;
    private String password;
}