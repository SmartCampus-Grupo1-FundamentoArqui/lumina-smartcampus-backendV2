package upc.edu.pe.smartcampusmaintenance.maintenance.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusmaintenance.maintenance.application.services.FacilityService;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.dto.FacilityRequest;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.Facility;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.MaintenanceState;

import java.util.List;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService service;

    @PostMapping
    public ResponseEntity<Facility> create(@RequestBody FacilityRequest request) {
        return ResponseEntity.ok(service.create(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Facility> update(@PathVariable Long id, @RequestBody FacilityRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping
    public ResponseEntity<List<Facility>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
