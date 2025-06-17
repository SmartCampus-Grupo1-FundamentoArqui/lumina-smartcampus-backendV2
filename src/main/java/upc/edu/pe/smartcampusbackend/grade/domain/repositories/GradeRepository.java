package upc.edu.pe.smartcampusbackend.grade.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusbackend.grade.domain.entities.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}