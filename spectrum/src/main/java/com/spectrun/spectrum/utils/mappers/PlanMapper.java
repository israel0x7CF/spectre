package com.spectrun.spectrum.utils.mappers;

import com.spectrun.spectrum.DTO.PlanDTO;
import com.spectrun.spectrum.models.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
@Mapper
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "billingCycle", target = "billingCycle")
    @Mapping(source = "features", target = "features")
    @Mapping(source = "maxUsers", target = "maxUsers")
    @Mapping(source = "maxContainers", target = "maxContainers")
    @Mapping(source = "supportLevel", target = "supportLevel")
    PlanDTO planToPlanDto(Plan plan);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "billingCycle", target = "billingCycle")
    @Mapping(source = "features", target = "features")
    @Mapping(source = "maxUsers", target = "maxUsers")
    @Mapping(source = "maxContainers", target = "maxContainers")
    @Mapping(source = "supportLevel", target = "supportLevel")
    Plan planDtoToPlan(PlanDTO planDto);
}
