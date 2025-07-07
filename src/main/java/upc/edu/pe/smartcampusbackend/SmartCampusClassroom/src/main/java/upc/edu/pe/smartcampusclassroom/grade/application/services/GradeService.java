package upc.edu.pe.smartcampusclassroom.grade.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusclassroom.grade.domain.dto.GradeRequest;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Classroom;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Grade;
import upc.edu.pe.smartcampusclassroom.grade.domain.repositories.ClassroomRepository;
import upc.edu.pe.smartcampusclassroom.grade.domain.repositories.GradeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository repository;
    private final ClassroomRepository classroomRepository;

    public Grade createGrade(GradeRequest request) {
        Grade grade = Grade.builder()
                .name(request.getName())
                .level(request.getLevel())
                .build();
        return repository.save(grade);
    }

    public Grade updateGrade(Long id, GradeRequest request) {
        Grade grade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        grade.setName(request.getName());
        grade.setLevel(request.getLevel());
        return repository.save(grade);
    }

    public List<Grade> getAllGrades() {
        return repository.findAll();
    }

    public Grade getGradeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    public void deleteGrade(Long id) {
        Grade grade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        List<Classroom> classrooms = classroomRepository.findByGrade_Id(id);
        if (classrooms != null && !classrooms.isEmpty()) {
            throw new RuntimeException("Cannot delete grade with existing classrooms");
        }
        repository.delete(grade);
    }
}
