package upc.edu.pe.smartcampusauth.auth.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusauth.auth.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
