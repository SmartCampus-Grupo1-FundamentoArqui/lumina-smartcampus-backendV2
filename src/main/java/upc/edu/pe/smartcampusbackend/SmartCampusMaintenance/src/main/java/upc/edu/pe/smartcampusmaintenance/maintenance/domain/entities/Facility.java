package upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal budget;

    @CreationTimestamp
    private LocalDate creation;

    private LocalDate finalization;

    private Integer period; // in months

    @Enumerated(EnumType.STRING)
    private MaintenanceState state;

    @PrePersist
    public void prePersist() {
        this.state = MaintenanceState.IN_PROGRESS;
        if (this.creation == null) this.creation = LocalDate.now();
        this.finalization = this.creation.plusMonths(this.period);
    }

    public void recalculateFinalization() {
        this.finalization = this.creation.plusMonths(this.period);
    }
}
