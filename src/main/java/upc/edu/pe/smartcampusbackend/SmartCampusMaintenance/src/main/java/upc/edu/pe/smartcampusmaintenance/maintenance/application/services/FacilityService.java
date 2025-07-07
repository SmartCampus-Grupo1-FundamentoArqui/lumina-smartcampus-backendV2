package upc.edu.pe.smartcampusmaintenance.maintenance.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.dto.FacilityRequest;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.Facility;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.MaintenanceState;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.repositories.FacilityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository repository;

    public Facility create(FacilityRequest request) {
        Facility facility = Facility.builder()
                .name(request.getName())
                .description(request.getDescription())
                .budget(request.getBudget())
                .period(request.getPeriod())
                .build();
        return repository.save(facility);
    }

    public List<Facility> getAll() {
        return repository.findAll();
    }

    public Facility update(Long id, FacilityRequest request) {
        Facility f = repository.findById(id).orElseThrow();
        f.setDescription(request.getDescription());
        f.setBudget(request.getBudget());
        f.setPeriod(request.getPeriod());
        f.recalculateFinalization();
        if (request.getState() != null)
            f.setState(MaintenanceState.valueOf(request.getState()));

        return repository.save(f);
    }

    public void delete(Long id) {
        Facility f = repository.findById(id).orElseThrow();
        if (f.getState() != MaintenanceState.FINALIZED)
            throw new RuntimeException("Facility must be finalized to delete.");
        repository.delete(f);
    }
}
