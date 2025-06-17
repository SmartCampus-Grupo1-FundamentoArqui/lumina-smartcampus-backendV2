package upc.edu.pe.smartcampusbackend.auth.domain.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}