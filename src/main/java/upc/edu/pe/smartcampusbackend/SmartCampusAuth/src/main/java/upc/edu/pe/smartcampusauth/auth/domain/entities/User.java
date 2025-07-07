package upc.edu.pe.smartcampusauth.auth.domain.entities;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    public enum Role {
        ADMIN, TEACHER
    }

}

