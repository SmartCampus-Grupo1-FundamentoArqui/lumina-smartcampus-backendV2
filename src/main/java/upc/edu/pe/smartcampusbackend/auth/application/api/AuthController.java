package upc.edu.pe.smartcampusbackend.auth.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusbackend.auth.application.services.AuthService;
import upc.edu.pe.smartcampusbackend.auth.domain.entities.User;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return authService.authenticate(username, password);
    }
}