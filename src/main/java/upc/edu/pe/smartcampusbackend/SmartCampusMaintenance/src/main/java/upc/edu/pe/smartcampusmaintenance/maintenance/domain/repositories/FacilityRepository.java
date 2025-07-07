package upc.edu.pe.smartcampusmaintenance.maintenance.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    // Additional query methods can be defined here if needed
}
