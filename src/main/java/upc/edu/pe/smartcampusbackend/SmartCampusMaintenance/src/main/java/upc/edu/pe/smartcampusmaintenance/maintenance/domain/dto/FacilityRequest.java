package upc.edu.pe.smartcampusmaintenance.maintenance.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FacilityRequest {
    private String name;
    private String description;
    private BigDecimal budget;
    private Integer period;
    private String state;
}