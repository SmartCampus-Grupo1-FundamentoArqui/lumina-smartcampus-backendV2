package upc.edu.pe.smartcampusmaintenance.maintenance.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.smartcampusmaintenance.maintenance.application.services.EquipmentService;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.dto.EquipmentRequest;
import upc.edu.pe.smartcampusmaintenance.maintenance.domain.entities.Equipment;

import java.util.List;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService service;

    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody EquipmentRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(@PathVariable Long id, @RequestBody EquipmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

