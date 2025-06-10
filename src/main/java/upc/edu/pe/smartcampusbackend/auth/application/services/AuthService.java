package upc.edu.pe.smartcampusbackend.auth.application.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import upc.edu.pe.smartcampusbackend.auth.domain.entities.User;
import upc.edu.pe.smartcampusbackend.auth.domain.repositories.UserRepository;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
