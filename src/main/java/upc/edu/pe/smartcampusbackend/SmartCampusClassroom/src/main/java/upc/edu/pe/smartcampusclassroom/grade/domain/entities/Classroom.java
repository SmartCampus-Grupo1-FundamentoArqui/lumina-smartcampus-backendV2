package upc.edu.pe.smartcampusclassroom.grade.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String section; // Ej. "A", "B", etc.
    private String roomNumber;// Ej. "Room 101"
    private String imageUrl; // URL de la imagen del aula
    private int capacity;       // Máximo número de estudiantes
    private int currentSize;    // Número actual de estudiantes inscritos

    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    @JsonBackReference
    private Grade grade; // Relación con la entidad Grade
}
