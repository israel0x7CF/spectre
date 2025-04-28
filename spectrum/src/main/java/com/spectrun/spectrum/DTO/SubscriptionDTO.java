package com.spectrun.spectrum.DTO;

import com.spectrun.spectrum.models.Plan;
import com.spectrun.spectrum.models.UsageLimits;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class SubscriptionDTO {

    private long id; // Correct capitalization for 'Id'
    private double pricing;
    private String status;
    private String billing_cycle;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private UsageLimits usageLimits;
    private long planId; // The ID of the Plan
}
