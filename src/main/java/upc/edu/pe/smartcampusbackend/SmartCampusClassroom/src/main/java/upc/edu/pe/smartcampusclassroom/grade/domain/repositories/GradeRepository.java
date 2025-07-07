package upc.edu.pe.smartcampusclassroom.grade.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}