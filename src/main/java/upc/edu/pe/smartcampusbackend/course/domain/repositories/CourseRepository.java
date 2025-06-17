package upc.edu.pe.smartcampusbackend.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByGradeId(Long gradeId);
}