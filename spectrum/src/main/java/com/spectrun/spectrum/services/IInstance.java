package com.spectrun.spectrum.services;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.models.Instances;

import java.util.List;

public interface IInstance {
    public InstanceDto createNewInstance(InstanceDto instanceConfig);
    public List<InstanceDto> getAllInstances();
    public List<InstanceDto> getAllUserInstances(long userid);
    public InstanceDto getInstanceById(long instanceId);


}
