package upc.edu.pe.smartcampusstudent.student.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import upc.edu.pe.smartcampusstudent.student.domain.dto.StudentRequest;
import upc.edu.pe.smartcampusstudent.student.domain.entities.Parent;
import upc.edu.pe.smartcampusstudent.student.domain.entities.Student;
import upc.edu.pe.smartcampusstudent.student.domain.repositories.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final WebClient webClient = WebClient.builder()
            .baseUrl(System.getenv().getOrDefault("CLASSROOM_SERVICE_BASE_URL", "http://localhost:8082"))
            .build();

    public Student registerStudent(StudentRequest request) {
        Parent parent = Parent.builder()
                .firstName(request.getParent().getFirstName())
                .lastNameFather(request.getParent().getLastNameFather())
                .lastNameMother(request.getParent().getLastNameMother())
                .dni(request.getParent().getDni())
                .phone(request.getParent().getPhone())
                .email(request.getParent().getEmail())
                .build();

        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastNameFather(request.getLastNameFather())
                .lastNameMother(request.getLastNameMother())
                .dni(request.getDni())
                .parent(parent)
                .build();

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(request.getFirstName());
        student.setLastNameFather(request.getLastNameFather());
        student.setLastNameMother(request.getLastNameMother());
        student.setDni(request.getDni());

        if (request.getParent() != null) {
            Parent parent = student.getParent();
            parent.setFirstName(request.getParent().getFirstName());
            parent.setLastNameFather(request.getParent().getLastNameFather());
            parent.setLastNameMother(request.getParent().getLastNameMother());
            parent.setDni(request.getParent().getDni());
            parent.setPhone(request.getParent().getPhone());
            parent.setEmail(request.getParent().getEmail());
            student.setParent(parent);
        }

        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student getByStudentAndClassroom(Long studentId, Long classroomId) {
        Student student = studentRepository.findByIdAndClassroomId(studentId, classroomId);
        if (student == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "Student not found in the specified classroom");
        }
        return student;
    }

    public List<Student> getByClassroom(Long classroomId) {
        List<Student> students = studentRepository.findByClassroomId(classroomId);
        if (students.isEmpty()) {
            throw new RuntimeException("No students found in the specified classroom");
        }
        return studentRepository.findByClassroomId(classroomId);
    }

    public String getParentEmail(Long studentId) {
        Student student = getById(studentId);
        Parent parent = student.getParent();
        if (parent == null || parent.getEmail() == null) {
            throw new RuntimeException("Parent email not found for student");
        }
        return parent.getEmail();
    }

    public Student assignClassroom(Long studentId, Long classroomId) {
        Student student = getById(studentId);
        Long previousClassroomId = student.getClassroomId();

        // Si ya está en el mismo classroom, no hacer nada
        if (previousClassroomId != null && previousClassroomId.equals(classroomId)) {
            return student;
        }

        // Validar si hay espacio en el classroom
        Boolean hasSpace = webClient.get()
                .uri("/classrooms/{id}/has-space", classroomId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(hasSpace)) {
            throw new RuntimeException("Classroom is full");
        }

        // Incrementar la carga del classroom
        webClient.put()
                .uri("/classrooms/{id}/increment", classroomId)
                .retrieve()
                .toBodilessEntity()
                .block();

        if(previousClassroomId != null) {
            // Disminuir la carga del classroom
            webClient.put()
                    .uri("/classrooms/{id}/decrement", previousClassroomId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }

        student.setClassroomId(classroomId);
        return studentRepository.save(student);

    }
}
