package com.spectrun.spectrum.utils.mappers;

import com.spectrun.spectrum.DTO.HostDto;
import com.spectrun.spectrum.models.Host;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface HostMapper {
    HostMapper HOST_MAPPER = Mappers.getMapper(HostMapper.class);

    HostDto hostToHostDto(Host host);
}
