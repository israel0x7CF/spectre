package com.spectrun.spectrum.services.Implementations;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.Enums.Status;
import com.spectrun.spectrum.MessageTemplate.createInstanceResponse;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Users;
import com.spectrun.spectrum.repositories.InstanceRepository;
import com.spectrun.spectrum.repositories.UserRepsoitory;
import com.spectrun.spectrum.services.IInstance;

import com.spectrun.spectrum.utils.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.spectrun.spectrum.utils.mappers.InstanceMapper.INSTANCE_MAPPER;

@Service
public class InstanceService implements IInstance {
    private InstanceRepository instanceRepository;
    private UserRepsoitory userRepsoitory;

    public InstanceDto convertToDto(Instances instace) {
        return INSTANCE_MAPPER.INSTANCE_MAPPER.instanceToInstaceDto(instace);
    }

    public Instances convertToEntity(InstanceDto instanceDto) {

        return INSTANCE_MAPPER.INSTANCE_MAPPER.instanceDtoToInstances(instanceDto);
    }


    public InstanceService(InstanceRepository instanceRepository,UserRepsoitory userRepsoitory) {
        this.instanceRepository = instanceRepository;
        this.userRepsoitory = userRepsoitory;   
    }


    @Override
    public InstanceDto createNewInstance(InstanceDto instanceConfig) {

        Instances newInstance = convertToEntity(instanceConfig);
        Users user = userRepsoitory.findById(instanceConfig.getUserId()).orElseThrow(()-> new UserNotFoundException("User Not Found"));
        newInstance.setUser(user);
        return  convertToDto(this.instanceRepository.save(newInstance));
    }


    @Override
    public List<InstanceDto> getAllInstances() {
        List<InstanceDto> activeInstances = this.instanceRepository.findAll().stream().map(instance -> convertToDto(instance)).collect(Collectors.toList());
        if(!activeInstances.isEmpty()){
            return activeInstances;
        }
        return null;
    }


    @Override
    public List<InstanceDto> getAllUserInstances(long userid) {
        Users user = userRepsoitory.findById(userid).orElse(null);
        if(user != null){
            List<Instances> userInstances = this.instanceRepository.findByUserId(user);
            if(userInstances!= null && !userInstances.isEmpty()){
                List<InstanceDto> userInstancesDtoList = userInstances.stream()
                        .map(instances -> INSTANCE_MAPPER.instanceToInstaceDto(instances))
                        .collect(Collectors.toList());
                return  userInstancesDtoList;
            }
        }

        return null;
    }

    @Override
    public InstanceDto getInstanceById(long instanceId) {
        Instances instance = this.instanceRepository.findById(instanceId).orElse(null);
        if(instance != null){
            return INSTANCE_MAPPER.instanceToInstaceDto(instance);
        }
        return null;
    }

    @Override
    public InstanceDto updateInstance(createInstanceResponse instanceResponse) {
//        instanceName='madmax', instanceCreationStatus=true, instanceAddress='http://localhost:40619', ins
//        tanceDbAddress='http://localhost:47487', instanceDbName='madmax_db', configurationFileLocation='/opt/container_configs/madmax/docker-compose.yml', customAddonsPath='/opt/container_configs/madmax/addons', adminUserName='admin', adminPasswo
//        rd='superadminsaas'}
    Instances instance = instanceRepository.findByinstanceName(instanceResponse.getInstanceName());
        if(instance == null){
            return null;
        }
        if(!instanceResponse.getInstanceCreationStatus()){
            instance.setStatus(Status.Failed);
        }
        instance.setStatus(Status.Active);
        instance.setInstanceaddress(instanceResponse.getInstanceAddress());
        instance.setAdminUserName(instanceResponse.getAdminUserName());
        instance.setAdminPassword(instanceResponse.getAdminUserName());
        instance.setConfigurationFileLocation(instanceResponse.getConfigurationFileLocation());
        instance.setInstancedbName(instanceResponse.getInstanceDbName());
        instance.setInstancedbaddress(instanceResponse.getInstanceAddress());
        instance.setInstanceaddress(instanceResponse.getInstanceAddress());
        Instances updatedInstance = instanceRepository.save(instance);
    return  INSTANCE_MAPPER.instanceToInstaceDto(updatedInstance);
    }

    @Override
    public Boolean deleteInstanceById(long id) {
        if (!instanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Instance with id " + id + " not found");
        }

        this.instanceRepository.deleteById(id);
        return  true;
    }

    @Override
    public List<InstanceDto> getInstanceByStatus(Status status) {

        return  this.instanceRepository
                .findByStatus(status).stream()
                .map(INSTANCE_MAPPER::instanceToInstaceDto)
                .collect(Collectors.toList());

    }


}
