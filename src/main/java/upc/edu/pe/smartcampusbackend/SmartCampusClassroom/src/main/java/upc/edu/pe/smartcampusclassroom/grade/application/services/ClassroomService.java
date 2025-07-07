package upc.edu.pe.smartcampusclassroom.grade.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusclassroom.grade.domain.dto.ClassroomRequest;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Classroom;
import upc.edu.pe.smartcampusclassroom.grade.domain.entities.Grade;
import upc.edu.pe.smartcampusclassroom.grade.domain.repositories.ClassroomRepository;
import upc.edu.pe.smartcampusclassroom.grade.domain.repositories.GradeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final GradeRepository gradeRepository;

    public Classroom createClassroom(ClassroomRequest request) {
        Grade grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        Classroom classroom = Classroom.builder()
                .section(request.getSection())
                .roomNumber(request.getRoomNumber())
                .capacity(request.getCapacity())
                .currentSize(0)
                .imageUrl(request.getImageUrl())
                .grade(grade)
                .build();

        return classroomRepository.save(classroom);
    }

    public Classroom updateClassroom(Long id, ClassroomRequest request) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        classroom.setSection(request.getSection());
        classroom.setRoomNumber(request.getRoomNumber());
        classroom.setCapacity(request.getCapacity());
        classroom.setImageUrl(request.getImageUrl());
        return classroomRepository.save(classroom);
    }


    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
    }

    public boolean hasSpace(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        return classroom.getCurrentSize() < classroom.getCapacity();
    }

    public void incrementStudentCount(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (classroom.getCurrentSize() >= classroom.getCapacity()) {
            throw new RuntimeException("Classroom is full");
        }

        classroom.setCurrentSize(classroom.getCurrentSize() + 1);
        classroomRepository.save(classroom);
    }

    public void decrementStudentCount(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (classroom.getCurrentSize() <= 0) {
            throw new RuntimeException("Classroom is empty");
        }

        classroom.setCurrentSize(classroom.getCurrentSize() - 1);
        classroomRepository.save(classroom);
    }

}
