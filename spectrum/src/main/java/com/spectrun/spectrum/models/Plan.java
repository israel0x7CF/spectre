package com.spectrun.spectrum.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Plan
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price; // price in USD or relevant currency
    private String billingCycle; // monthly, annually, etc.

    @ElementCollection
    private List<String> features;

    private Integer maxUsers; // maximum users for the plan
    private Integer maxContainers; // maximum number of containers
    private String supportLevel; // e.g., "priority"


}
