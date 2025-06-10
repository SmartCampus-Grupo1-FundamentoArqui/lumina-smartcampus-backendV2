package upc.edu.pe.smartcampusbackend.course.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Course;
import upc.edu.pe.smartcampusbackend.course.domain.entities.Grade;
import upc.edu.pe.smartcampusbackend.course.domain.repositories.CourseRepository;
import upc.edu.pe.smartcampusbackend.course.domain.repositories.GradeRepository;
import java.util.List;
import java.util.Optional;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    // Crear un nuevo grado
    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    // Crear un nuevo curso
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // Asignar un curso a un grado (relacionar curso con grado)
    public Course assignCourseToGrade(Long gradeId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findById(gradeId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (grade.isPresent() && course.isPresent()) {
            // Aquí puedes agregar la lógica de asignación
            // Por ejemplo, un curso podría ser asignado a un grado específico.
            // Actualmente, no hemos definido una relación entre ambos, pero puedes agregarlo.

            return courseRepository.save(course.get());
        } else {
            // Lógica de error si el grado o curso no se encuentran
            return null;
        }
    }

    // Obtener todos los grados
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    // Obtener todos los cursos
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
