package com.spectrun.spectrum.services;

import com.spectrun.spectrum.DTO.PlanDTO;
import java.util.List;
public interface IPlan {
    public PlanDTO createSubscriptionPlan(PlanDTO plan);
    public List<PlanDTO> getAllPlans();
}
