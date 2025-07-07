package upc.edu.pe.smartcampusstudent.student.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastNameFather;
    private String lastNameMother;
    private String dni;

    private Long classroomId;


    @OneToOne(cascade = CascadeType.ALL)
    private Parent parent;
}
