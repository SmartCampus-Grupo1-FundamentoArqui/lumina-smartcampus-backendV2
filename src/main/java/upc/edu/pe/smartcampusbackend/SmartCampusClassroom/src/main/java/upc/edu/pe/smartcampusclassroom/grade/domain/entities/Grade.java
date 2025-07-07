package upc.edu.pe.smartcampusclassroom.grade.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Ej. "1st Grade"
    private String level;       // Ej. "Primary"

    @OneToMany(mappedBy = "grade")
    @JsonManagedReference
    private List<Classroom> classrooms; // Relación con Classroom

}