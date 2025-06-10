package upc.edu.pe.smartcampusbackend.auth.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusbackend.auth.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
