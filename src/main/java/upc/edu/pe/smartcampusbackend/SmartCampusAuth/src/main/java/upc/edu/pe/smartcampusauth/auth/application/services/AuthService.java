package upc.edu.pe.smartcampusauth.auth.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.web.reactive.function.client.WebClient;
import upc.edu.pe.smartcampusauth.auth.config.security.JwtService;
import upc.edu.pe.smartcampusauth.auth.domain.dto.AuthRequest;
import upc.edu.pe.smartcampusauth.auth.domain.dto.AuthResponse;
import upc.edu.pe.smartcampusauth.auth.domain.dto.RegisterRequest;
import upc.edu.pe.smartcampusauth.auth.domain.dto.TeacherResponse;
import upc.edu.pe.smartcampusauth.auth.domain.entities.User;
import upc.edu.pe.smartcampusauth.auth.domain.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Usa variable de entorno para la URL base del microservicio teacher
    private static final String TEACHER_SERVICE_BASE_URL =
            System.getenv().getOrDefault("TEACHER_SERVICE_BASE_URL", "http://localhost:8084");

    private final WebClient webClient = WebClient.builder()
            .baseUrl(TEACHER_SERVICE_BASE_URL)
            .build();

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.ADMIN)
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }
    public AuthResponse authenticateTeacher(AuthRequest request) {
        // 1. Obtener el profesor desde user-service
        TeacherResponse teacher = webClient.get()
                .uri("/teachers/email/{email}", request.getEmail())
                .retrieve()
                .bodyToMono(TeacherResponse.class)
                .block();

        if (teacher == null) {
            throw new RuntimeException("Invalid credentials");
        }

        // 2. Verificar password
        if (!passwordEncoder.matches(request.getPassword(), teacher.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3. Crear token con rol TEACHER
        User mockUser = User.builder()
                .email(teacher.getEmail())
                .role(User.Role.TEACHER)
                .build();

        String token = jwtService.generateToken(mockUser);
        return new AuthResponse(token);
    }
}
