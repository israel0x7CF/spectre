package com.spectrun.spectrum.services.Implementations;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Users;
import com.spectrun.spectrum.repositories.InstanceRepository;
import com.spectrun.spectrum.repositories.UserRepsoitory;
import com.spectrun.spectrum.services.IInstance;

import org.springframework.stereotype.Service;

import java.util.List;
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


    public InstanceService(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }


    @Override
    public InstanceDto createNewInstance(InstanceDto instanceConfig) {
        Instances newInstance = convertToEntity(instanceConfig);
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




}
