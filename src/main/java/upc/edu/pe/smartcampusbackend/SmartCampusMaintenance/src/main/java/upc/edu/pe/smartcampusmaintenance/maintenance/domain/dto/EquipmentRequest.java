package upc.edu.pe.smartcampusmaintenance.maintenance.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentRequest {
    private String name;
    private Integer quantity;
    private BigDecimal budget;
    private Integer period;
    private String state; // Optional: for update
}
