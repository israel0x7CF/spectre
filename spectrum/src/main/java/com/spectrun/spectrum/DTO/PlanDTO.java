package com.spectrun.spectrum.DTO;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlanDTO {
    private Long id;

    private String name;
    private Double price; // price in USD or relevant currency
    private String billingCycle; // monthly, annually, etc.
    private List<String> features;
    private Integer maxUsers; // maximum users for the plan
    private Integer maxContainers; // maximum number of containers
    private String supportLevel; // e.g., "priority"
}
