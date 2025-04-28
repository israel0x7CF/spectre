package com.spectrun.spectrum.services;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.Enums.Status;
import com.spectrun.spectrum.MessageTemplate.createInstanceResponse;

import java.util.List;

public interface IInstance {
    public InstanceDto createNewInstance(InstanceDto instanceConfig);
    public List<InstanceDto> getAllInstances();
    public List<InstanceDto> getAllUserInstances(long userid);
    public InstanceDto getInstanceById(long instanceId);
    public InstanceDto updateInstance(createInstanceResponse Instance);
    public Boolean deleteInstanceById(long id);
    public List<InstanceDto> getInstanceByStatus(Status status);
}
