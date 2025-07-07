package upc.edu.pe.smartcampusgradebook.gradebook.domain.repositories;

import upc.edu.pe.smartcampusgradebook.gradebook.domain.entities.GradeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeEntryRepository extends JpaRepository<GradeEntry, Long> {
    Optional<GradeEntry> findByStudentIdAndCourseId(Long studentId, Long courseId);
    Optional<GradeEntry> findByStudentIdAndCourseIdAndClassroomId(Long studentId, Long courseId, Long classroomId);
    List<GradeEntry> findByCourseIdAndClassroomId(Long courseId, Long classroomId);
    List<GradeEntry> findNameByCourseId(Long courseId);

}