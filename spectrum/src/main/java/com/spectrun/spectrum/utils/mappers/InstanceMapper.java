package com.spectrun.spectrum.utils.mappers;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.models.Instances;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InstanceMapper {
    InstanceMapper INSTANCE_MAPPER = Mappers.getMapper(InstanceMapper.class);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "instanceName", source = "instanceName")
    @Mapping(target = "instancedbName", source = "instancedbName")
    @Mapping(target = "instancedbaddress", source = "instancedbaddress")
    @Mapping(target = "instanceaddress", source = "instanceaddress")
    @Mapping(target = "supportedVersion", source = "supportedVersion")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "configurationFileLocation", source = "configurationFileLocation")
    @Mapping(target = "adminUserName", source = "adminUserName")
    @Mapping(target = "adminPassword", source = "adminPassword")
    @Mapping(target = "user", source = "users")
    @Mapping(target = "createdOn", source = "createdOn")
    @Mapping(target = "updatedOn", source = "updatedOn")
    Instances instanceDtoToInstances(InstanceDto instanceDto);
    InstanceDto instanceToInstaceDto(Instances instances);

}
