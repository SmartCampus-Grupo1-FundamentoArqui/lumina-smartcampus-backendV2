package upc.edu.pe.smartcampususer.teacher.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampususer.teacher.domain.entities.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
}
