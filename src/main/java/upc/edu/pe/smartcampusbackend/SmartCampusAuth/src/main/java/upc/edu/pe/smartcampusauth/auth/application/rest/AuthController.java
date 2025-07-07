package upc.edu.pe.smartcampusauth.auth.application.rest;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import upc.edu.pe.smartcampusauth.auth.application.services.AuthService;
import upc.edu.pe.smartcampusauth.auth.domain.dto.AuthRequest;
import upc.edu.pe.smartcampusauth.auth.domain.dto.AuthResponse;
import upc.edu.pe.smartcampusauth.auth.domain.dto.RegisterRequest;
import upc.edu.pe.smartcampusauth.auth.domain.repositories.UserRepository;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String header) {
        String email = header.replace("Bearer ", "");
        var user = userRepository.findByEmail(email).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login-teacher")
    public ResponseEntity<AuthResponse> loginTeacher(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticateTeacher(request));
    }
}