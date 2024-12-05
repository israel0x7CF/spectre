package com.spectrun.spectrum.services.Implementations;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.DTO.ModuleDto;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Modules;
import com.spectrun.spectrum.models.installModules;
import com.spectrun.spectrum.repositories.InstallModuleRepository;
import com.spectrun.spectrum.services.IInstallModule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallModuleService implements IInstallModule {
    InstallModuleRepository installModuleRepository;
    InstanceService instanceService;
    ModuleService moduleService;
    public InstallModuleService(InstallModuleRepository installModuleRepository, InstanceService instanceService,ModuleService moduleService) {
        this.installModuleRepository = installModuleRepository;
        this.instanceService = instanceService;
        this.moduleService = moduleService;

    }


    @Override
    public List<installModules> getInstalledModules(long instanceId) {
        return null;
    }

    @Override
    public installModules installNewModule(long moduleId,long instanceId) {
        Instances instance = this.instanceService.convertToEntity(instanceService.getInstanceById(instanceId));
        Modules module = this.moduleService.moduleMapper(this.moduleService.getModuleById(moduleId));
        if(instance != null && module !=null){
            installModules moduleToInstall = installModules.builder()
                    .instance(instance)
                    .module(module)
                    .build();
            this.installModuleRepository.save(moduleToInstall);
        }
        return null;
    }

    @Override
    public installModules unInstallModules(long moduleId) {
        return null;
    }
}
