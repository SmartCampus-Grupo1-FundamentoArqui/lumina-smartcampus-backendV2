package upc.edu.pe.smartcampusstudent.student.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusstudent.student.domain.entities.Student;


import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByClassroomId(Long classroomId);
    Student findByIdAndClassroomId(Long studentId, Long classroomId);
}

