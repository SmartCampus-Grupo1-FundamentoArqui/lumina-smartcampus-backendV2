package upc.edu.pe.smartcampusgradebook.gradebook.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "grade_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long courseId;
    private Long classroomId;
    private Long teacherId;

    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double nota4;
    private Double nota5;
    private Double nota6;
    private Double nota7;

    private Double average;

    @Enumerated(EnumType.STRING)
    private GradeState state;

    private LocalDate updatedAt;

    public void recalculate() {
        double sum = 0;
        int count = 0;
        Double[] notas = {nota1, nota2, nota3, nota4, nota5, nota6, nota7};

        for (Double n : notas) {
            if (n != null) {
                sum += n;
                count++;
            }
        }

        double avg = count == 0 ? 0 : sum / count;
        this.average = new java.math.BigDecimal(avg)
                .setScale(2, java.math.RoundingMode.HALF_UP)
                .doubleValue();
        this.state = this.average >= 11 ? GradeState.APPROVED : GradeState.DISAPPROVED;
        this.updatedAt = LocalDate.now();
    }
}

