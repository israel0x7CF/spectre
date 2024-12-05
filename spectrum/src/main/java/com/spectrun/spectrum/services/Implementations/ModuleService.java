package com.spectrun.spectrum.services.Implementations;
import com.spectrun.spectrum.DTO.ModuleDto;
import com.spectrun.spectrum.models.Modules;
import com.spectrun.spectrum.repositories.ModuleRepository;
import com.spectrun.spectrum.services.IModuleService;
import com.spectrun.spectrum.utils.mappers.ModuleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleService implements IModuleService {
    ModuleRepository moduleRepository ;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }
    public Modules moduleMapper(ModuleDto moduleDto){
        return ModuleMapper.INSTANCE.toEntity(moduleDto);
    }
    public ModuleDto entityToDto(Modules modules){
        return  ModuleMapper.INSTANCE.toDto(modules);
    }

    @Override
    public ModuleDto addNewmodule(ModuleDto module) {
        Modules modules  = moduleMapper(module);
        ModuleDto dto  = entityToDto(this.moduleRepository.save(modules));
        return dto;
    }

    @Override
    public List<ModuleDto> getAllModules() {
        List<ModuleDto> moduleList = this.moduleRepository.findAll()
                .stream()
                .map(
                        module -> entityToDto(module)
                ).collect(Collectors.toList());
        return  moduleList;
    }

    @Override
    public ModuleDto getModuleById(long id) {
        Modules module = this.moduleRepository.findById(id).orElse(null);
        if(module != null){
            return  this.entityToDto(module);

        }
        return null;
    }
}
