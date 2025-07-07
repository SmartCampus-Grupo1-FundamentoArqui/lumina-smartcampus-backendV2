package upc.edu.pe.smartcampusstudent.student.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusstudent.student.domain.entities.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {}

