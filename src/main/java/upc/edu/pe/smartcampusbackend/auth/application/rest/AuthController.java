package upc.edu.pe.smartcampusbackend.auth.application.rest;

import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.auth.application.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import upc.edu.pe.smartcampusbackend.auth.domain.dto.AuthRequest;
import upc.edu.pe.smartcampusbackend.auth.domain.dto.AuthResponse;
import upc.edu.pe.smartcampusbackend.auth.domain.dto.RegisterRequest;
import upc.edu.pe.smartcampusbackend.auth.domain.repositories.UserRepository;

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
}