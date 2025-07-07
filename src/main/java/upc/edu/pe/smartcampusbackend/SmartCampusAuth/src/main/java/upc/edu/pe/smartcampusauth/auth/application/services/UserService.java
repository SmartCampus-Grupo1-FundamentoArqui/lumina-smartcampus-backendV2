package upc.edu.pe.smartcampusauth.auth.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusauth.auth.domain.repositories.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        new ArrayList<>()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}