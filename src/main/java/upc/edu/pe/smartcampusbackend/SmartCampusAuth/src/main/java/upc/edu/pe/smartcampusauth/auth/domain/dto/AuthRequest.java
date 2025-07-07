package upc.edu.pe.smartcampusauth.auth.domain.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}