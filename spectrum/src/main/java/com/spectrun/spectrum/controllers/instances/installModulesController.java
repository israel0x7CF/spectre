package com.spectrun.spectrum.controllers.instances;

import com.spectrun.spectrum.DTO.InstallModuleDTO;
import com.spectrun.spectrum.DTO.ModuleDto;
import com.spectrun.spectrum.models.installModules;
import com.spectrun.spectrum.services.Implementations.InstallModuleService;
import com.spectrun.spectrum.services.Implementations.InstanceService;
import com.spectrun.spectrum.services.Implementations.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/install")
public class installModulesController {
    InstallModuleService installModuleService;

    @Autowired
    public installModulesController(InstallModuleService installModuleService) {
        this.installModuleService = installModuleService;

    }
    @PostMapping("/module")
    public ResponseEntity<?> installModule(@RequestBody InstallModuleDTO instllationInfo){
        installModules module = this.installModuleService.installNewModule(instllationInfo.getModule_id(),instllationInfo.getInstance_id());
        if(module != null ){
            return  new ResponseEntity<>(module, HttpStatus.OK);
        }
        return  new ResponseEntity<>("installation response", HttpStatus.BAD_REQUEST);
    }


}
