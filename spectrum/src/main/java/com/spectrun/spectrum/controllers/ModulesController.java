package com.spectrun.spectrum.controllers;

import com.spectrun.spectrum.DTO.InstallModuleDTO;
import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.models.installModules;
import com.spectrun.spectrum.services.Implementations.InstallModuleService;
import com.spectrun.spectrum.services.Implementations.InstanceService;
import com.spectrun.spectrum.utils.API.Request;
import com.spectrun.spectrum.utils.API.RequestDTO.RemoteInstanceModule;
import com.spectrun.spectrum.utils.API.ResponseBody.ResponseBody;
import com.spectrun.spectrum.utils.API.ResponseBody.ModuleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.spectrun.spectrum.utils.URLParser.getPortFromAddress;


@RestController
@RequestMapping("/api/v1/install")
public class ModulesController {
    InstallModuleService installModuleService;
    InstanceService instanceService;
    @Autowired
    public ModulesController(InstallModuleService installModuleService,InstanceService instanceService) {
        this.installModuleService = installModuleService;
        this.instanceService = instanceService;

    }
    @PostMapping("/module")
    public ResponseEntity<?> installModule(@RequestBody InstallModuleDTO instllationInfo){
        installModules module = this.installModuleService.installNewModule(instllationInfo.getModule_id(),instllationInfo.getInstance_id());
        if(module != null ){
            return  new ResponseEntity<>(module, HttpStatus.OK);
        }
        return  new ResponseEntity<>("installation response", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/instance_modules/{id}")
    public ResponseEntity<?> getInstalledModules(@PathVariable long id){
        try{
            InstanceDto instance = this.instanceService.getInstanceById(id);
            int port =Integer.parseInt(getPortFromAddress(instance.getInstanceaddress()));
            RemoteInstanceModule remoteInstance= new RemoteInstanceModule();
            remoteInstance.setAddress("localhost");
            remoteInstance.setPort(port);
            remoteInstance.setDbName(instance.getInstancedbName());
            remoteInstance.setPassword(instance.getAdminPassword());
            remoteInstance.setUsername(instance.getAdminUserName());
            Request<RemoteInstanceModule, ModuleStatus> request = new Request<>();

            ResponseBody<ModuleStatus> result = request.handleApiCall(remoteInstance,"http://127.0.0.1:5050/api/v1/modules/",new TypeReference<ResponseBody<ModuleStatus>>() {});
            if(result.getStatus() != 404){
                return new ResponseEntity<>("fetch faild", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}

