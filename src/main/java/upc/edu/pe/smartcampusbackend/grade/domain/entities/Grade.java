package upc.edu.pe.smartcampusbackend.grade.domain.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private String name;     // Ej. "1st Grade"
    private String level;    // Ej. "Primary", "Secondary"
}