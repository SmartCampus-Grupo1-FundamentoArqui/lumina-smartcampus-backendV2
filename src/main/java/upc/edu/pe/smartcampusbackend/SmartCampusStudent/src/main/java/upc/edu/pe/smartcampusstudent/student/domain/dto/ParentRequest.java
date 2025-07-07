package upc.edu.pe.smartcampusstudent.student.domain.dto;

import lombok.Data;

@Data
public class ParentRequest {
    private String firstName;
    private String lastNameFather;
    private String lastNameMother;
    private String dni;
    private String phone;
    private String email;
}
