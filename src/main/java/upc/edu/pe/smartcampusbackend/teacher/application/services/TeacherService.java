package upc.edu.pe.smartcampusbackend.teacher.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.teacher.domain.dto.TeacherRequest;
import upc.edu.pe.smartcampusbackend.teacher.domain.entities.Teacher;
import upc.edu.pe.smartcampusbackend.teacher.domain.repositories.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;

    public Teacher createTeacher(TeacherRequest request) {
        Teacher teacher = Teacher.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        return repository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return repository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }
}
