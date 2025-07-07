package upc.edu.pe.smartcampuscourse.course.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampuscourse.course.domain.entities.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByClassroomId(Long classroomId);
    List<Course> findByTeacherId(Long teacherId);
    Course findByIdAndTeacherId(Long courseId, Long teacherId);
}