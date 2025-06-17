package upc.edu.pe.smartcampusbackend.auth.application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.auth.config.security.JwtService;
import upc.edu.pe.smartcampusbackend.auth.domain.dto.AuthRequest;
import upc.edu.pe.smartcampusbackend.auth.domain.dto.AuthResponse;
import upc.edu.pe.smartcampusbackend.auth.domain.dto.RegisterRequest;
import upc.edu.pe.smartcampusbackend.auth.domain.entities.User;
import upc.edu.pe.smartcampusbackend.auth.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
}
