package upc.edu.pe.smartcampusbackend.teacher.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Course;
import upc.edu.pe.smartcampusbackend.course.domain.repositories.CourseRepository;
import upc.edu.pe.smartcampusbackend.teacher.domain.entities.Teacher;
import upc.edu.pe.smartcampusbackend.teacher.domain.repositories.TeacherRepository;
import java.util.Optional;


@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Crear un nuevo profesor
    public Teacher createTeacher(Teacher teacher) {
        teacher.setPassword(encoder.encode(teacher.getPassword()));  // Cifra la contraseña
        return teacherRepository.save(teacher);
    }

    // Asignar un profesor a un curso
    public boolean assignTeacherToCourse(Long teacherId, Long courseId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (teacher.isPresent() && course.isPresent()) {
            // Aquí puedes agregar la lógica de asignación de profesores a cursos
            // Por ejemplo, agregar el profesor al curso. Esto depende de cómo estructures la relación.
            return true;
        }
        return false;
    }
}
