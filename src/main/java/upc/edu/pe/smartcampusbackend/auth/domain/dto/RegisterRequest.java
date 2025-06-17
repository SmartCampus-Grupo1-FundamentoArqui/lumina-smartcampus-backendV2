package upc.edu.pe.smartcampusbackend.auth.domain.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;

}
