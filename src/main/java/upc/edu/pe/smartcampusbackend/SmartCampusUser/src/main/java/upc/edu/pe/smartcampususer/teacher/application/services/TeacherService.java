package upc.edu.pe.smartcampususer.teacher.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampususer.teacher.application.infrastructure.producer.RabbitMQProducer;
import upc.edu.pe.smartcampususer.teacher.domain.dto.EmailMessage;
import upc.edu.pe.smartcampususer.teacher.domain.dto.TeacherRequest;
import upc.edu.pe.smartcampususer.teacher.domain.entities.Teacher;
import upc.edu.pe.smartcampususer.teacher.domain.repositories.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RabbitMQProducer rabbitMQProducer;

    public Teacher createTeacher(TeacherRequest request) {
        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName())
                .lastNameFather(request.getLastNameFather())
                .lastNameMother(request.getLastNameMother())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return repository.save(teacher);
    }

    public Teacher updateTeacher(Long id, TeacherRequest request) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setFirstName(request.getFirstName());
        teacher.setLastNameFather(request.getLastNameFather());
        teacher.setLastNameMother(request.getLastNameMother());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        if (request.getPassword() != null) {
            teacher.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return repository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return repository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    public Teacher findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    public void notifyParent(String parentEmail, String subject, String body) {
        EmailMessage msg = new EmailMessage();
        msg.setParentEmail(parentEmail);
        msg.setSubject(subject);
        msg.setBody(body);

        rabbitMQProducer.sendEmailMessage(msg);
    }
}