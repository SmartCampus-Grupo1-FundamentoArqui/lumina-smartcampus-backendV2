package upc.edu.pe.smartcampusstudent.student.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastNameFather;
    private String lastNameMother;
    private String dni;
    private String phone;
    private String email;
}
