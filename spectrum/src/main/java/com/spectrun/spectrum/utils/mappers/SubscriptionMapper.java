package com.spectrun.spectrum.utils.mappers;

import com.spectrun.spectrum.DTO.SubscriptionDTO;
import com.spectrun.spectrum.models.Subscriptions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
@Mapper
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    // Mapping from entity to DTO, including the full Plan object
    @Mapping(source = "plan.id", target = "planId")
    SubscriptionDTO toDto(Subscriptions subscription);

    // Mapping from SubscriptionDTO to Subscriptions, setting plan.id from planId
    @Mapping(source = "planId", target = "plan.id")
    Subscriptions toEntity(SubscriptionDTO subscriptionDTO);
}
