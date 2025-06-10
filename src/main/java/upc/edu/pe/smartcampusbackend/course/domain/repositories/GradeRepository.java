package upc.edu.pe.smartcampusbackend.course.domain.repositories;

import upc.edu.pe.smartcampusbackend.course.domain.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
