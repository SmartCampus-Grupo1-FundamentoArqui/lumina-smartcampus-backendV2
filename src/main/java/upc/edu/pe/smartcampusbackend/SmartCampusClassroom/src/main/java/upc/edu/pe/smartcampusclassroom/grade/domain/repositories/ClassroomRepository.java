package upc.edu.pe.smartcampusclassroom.grade.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Classroom;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByGrade_Id(Long gradeId);
}
