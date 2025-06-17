package upc.edu.pe.smartcampusbackend.grade.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.grade.domain.dto.GradeRequest;
import upc.edu.pe.smartcampusbackend.grade.domain.entities.Grade;
import upc.edu.pe.smartcampusbackend.grade.domain.repositories.GradeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository repository;

    public Grade createGrade(GradeRequest request) {
        Grade grade = Grade.builder()
                .name(request.getName())
                .level(request.getLevel())
                .build();
        return repository.save(grade);
    }

    public List<Grade> getAllGrades() {
        return repository.findAll();
    }

    public Grade getGradeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Grade not found"));
    }
}
