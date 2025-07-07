package upc.edu.pe.smartcampusmaintenance.maintenance.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.dto.EquipmentRequest;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.Equipment;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.MaintenanceState;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.repositories.EquipmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository repository;

    public Equipment create(EquipmentRequest request) {
        Equipment e = Equipment.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .budget(request.getBudget())
                .period(request.getPeriod())
                .build();
        return repository.save(e);
    }

    public List<Equipment> getAll() {
        return repository.findAll();
    }

    public Equipment update(Long id, EquipmentRequest request) {
        Equipment e = repository.findById(id).orElseThrow();
        e.setQuantity(request.getQuantity());
        e.setBudget(request.getBudget());
        e.setPeriod(request.getPeriod());
        e.recalculateFinalization();
        if (request.getState() != null)
            e.setState(MaintenanceState.valueOf(request.getState()));
        return repository.save(e);
    }

    public void delete(Long id) {
        Equipment e = repository.findById(id).orElseThrow();
        if (e.getState() != MaintenanceState.FINALIZED)
            throw new RuntimeException("Equipment must be finalized to delete.");
        repository.delete(e);
    }
}
