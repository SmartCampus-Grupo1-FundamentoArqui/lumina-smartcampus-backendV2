package upc.edu.pe.smartcampuscourse.course.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long classroomId;     // ID de grado al que pertenece

    private Long teacherId;   // ID del profesor asignado

    private String schedule;  // formato simple "Mon-Wed 08:00-09:30"

    private String imageUrl; // URL de la imagen del curso
}

