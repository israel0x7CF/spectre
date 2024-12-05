package com.spectrun.spectrum.services.Implementations;

import com.spectrun.spectrum.DTO.PlanDTO;
import com.spectrun.spectrum.models.Plan;
import com.spectrun.spectrum.repositories.PlanRepository;
import com.spectrun.spectrum.services.IPlan;
import com.spectrun.spectrum.utils.mappers.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService implements IPlan {
    private PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }
    public PlanDTO convertToDto(Plan plan) {
        return PlanMapper.INSTANCE.planToPlanDto(plan);
    }

    public Plan convertToEntity(PlanDTO planDto) {
        return PlanMapper.INSTANCE.planDtoToPlan(planDto);
    }

    @Override
    public PlanDTO createSubscriptionPlan(PlanDTO newPlan) {
        Plan plan = convertToEntity(newPlan);

        Plan planEntity = this.planRepository.save(plan);
        return  this.convertToDto(planEntity);

    }

    @Override
    public List<PlanDTO> getAllPlans() {
        List<Plan> subscriptionPlans = this.planRepository.findAll();
        List <PlanDTO> planDTOList = subscriptionPlans.stream().map(plan -> convertToDto(plan)).collect(Collectors.toList());
        return  planDTOList;
    }
}
