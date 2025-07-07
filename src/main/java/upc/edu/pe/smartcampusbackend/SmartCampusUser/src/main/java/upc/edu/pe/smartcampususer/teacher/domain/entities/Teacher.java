package upc.edu.pe.smartcampususer.teacher.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastNameFather;

    private String lastNameMother;

    @Column(unique = true)
    private String email;

    private String phone;

    private String password; // Hash bcrypt
}