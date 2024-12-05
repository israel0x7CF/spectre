package com.spectrun.spectrum.utils.mappers;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.DTO.UserDTO;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping from Users entity to UserDTO
    @Mapping(source = "instances", target = "instances")
    UserDTO userToUserDto(Users user);

    // Mapping from UserDTO to Users entity
    @Mapping(target = "instances", source = "instances")
    Users userDtoToUser(UserDTO userDTO);

    // Custom method to map Instances to InstanceDto
    default InstanceDto mapToInstanceDto(Instances instances) {
        if (instances == null) {
            return null;
        }
        return InstanceDto.builder()
                .id(instances.getId())
                .instanceName(instances.getInstanceName())
                .instancedbName(instances.getInstancedbName())
                .instancedbaddress(instances.getInstancedbaddress())
                .instanceaddress(instances.getInstanceaddress())
                .supportedVersion(instances.getSupportedVersion())
                .status(instances.getStatus())
                .configurationFileLocation(instances.getConfigurationFileLocation())
                .adminUserName(instances.getAdminUserName())
                .adminPassword(instances.getAdminPassword())
                .createdOn(instances.getCreatedOn())
                .updatedOn(instances.getUpdatedOn())
                .build();
    }

    // Custom method to map InstanceDto to Instances
    default Instances mapToInstances(InstanceDto dto) {
        if (dto == null) {
            return null;
        }
        return Instances.builder()
                .id(dto.getId())
                .instanceName(dto.getInstanceName())
                .instancedbName(dto.getInstancedbName())
                .instancedbaddress(dto.getInstancedbaddress())
                .instanceaddress(dto.getInstanceaddress())
                .supportedVersion(dto.getSupportedVersion())
                .status(dto.getStatus())
                .configurationFileLocation(dto.getConfigurationFileLocation())
                .adminUserName(dto.getAdminUserName())
                .adminPassword(dto.getAdminPassword())
                .createdOn(dto.getCreatedOn())
                .updatedOn(dto.getUpdatedOn())
                .build();
    }
}
