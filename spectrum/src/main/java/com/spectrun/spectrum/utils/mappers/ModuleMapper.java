package com.spectrun.spectrum.utils.mappers;

import com.spectrun.spectrum.DTO.ModuleDto;
import com.spectrun.spectrum.models.Modules;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ModuleMapper {
    ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

    ModuleDto toDto(Modules modules);
    Modules toEntity(ModuleDto moduleDto);

}
