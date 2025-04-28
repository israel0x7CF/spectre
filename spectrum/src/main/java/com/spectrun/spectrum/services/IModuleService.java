package com.spectrun.spectrum.services;

import com.spectrun.spectrum.DTO.ModuleDto;

import java.util.List;

public interface IModuleService {
    public ModuleDto addNewmodule(ModuleDto module);
    public List<ModuleDto> getAllModules();
    public ModuleDto getModuleById(long id);
}
