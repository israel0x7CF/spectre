package com.spectrun.spectrum.controllers.instances;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spectrun.spectrum.DTO.*;
import com.spectrun.spectrum.Enums.Status;
import com.spectrun.spectrum.MessageTemplate.createInstanceTemplate;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Subscriptions;
import com.spectrun.spectrum.services.Implementations.InstanceService;
import com.spectrun.spectrum.services.Implementations.ModuleService;
import com.spectrun.spectrum.services.Implementations.UserService;
import com.spectrun.spectrum.utils.API.Request;
import com.spectrun.spectrum.utils.API.RequestDTO.InstallModuleDto;
import com.spectrun.spectrum.utils.API.ResponseBody.ResponseBody;
import com.spectrun.spectrum.utils.API.ResponseDTO.moduleInstallResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static com.spectrun.spectrum.utils.URLParser.getPortFromAddress;

@RestController
@RequestMapping("/api/v1/Instances")
@Deprecated
public class InstanceController {
    private InstanceService instanceService;
    private ModuleService moduleService;
    private UserService userService;
    Logger logger = Logger.getLogger(InstanceController.class.getName());
    private KafkaTemplate<String, createInstanceTemplate> createInstance;
    public InstanceController(ModuleService moduleService,InstanceService instanceService,KafkaTemplate<String, createInstanceTemplate> createInstanceTemplate,UserService userService) {
        this.instanceService = instanceService;
        this .createInstance = createInstanceTemplate;
        this.userService = userService;
        this.moduleService = moduleService;
    }


    @PostMapping("/create/instance")
    public ResponseEntity<?> createNewInstance(@RequestBody InstanceModuleDto installationData){
        String InstanceName = installationData.getInstallationInstance().getInstanceName();
        ModuleDto installationModule = installationData.getInstallationModule();
        //get the user from context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO subscriptionUser = this.userService.getUserByEmail(username);
        if(subscriptionUser != null){
            logger.info("No Subs User");
                Subscriptions subscription = subscriptionUser.getSubscription();

            if(subscription != null){
                logger.info(" Subs Found");
                long limit = subscriptionUser.getSubscription().getUsageLimits().getInstanceLimit();
                List<Instances> userInstances = subscriptionUser.getInstances();
                String active = String.valueOf(Status.Active);
                long activeUserInstances = userInstances.stream()
                        .filter(x-> String.valueOf(x.getStatus()).equals(active))
                        .count();
                if(limit > activeUserInstances){
                    //todo:create instances
                    //todo: remove this logic and depricate this endpoint
                    createInstanceTemplate template = new createInstanceTemplate(InstanceName,installationModule.getModuleName(),installationModule.getModulePath());
                    this.createInstance.send("create_instance",template);
                }
                else{
                    return new ResponseEntity<>(
                            "Reached Instance Limit for Your Subscribtion. Either Remove Instances Or Upgrade Plan",
                            HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>(
                        "No Subs",
                        HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return  null;
        }
        // todo:
        // 1 create the instance service, save only by name
        InstanceDto newInstance = InstanceDto.builder()
                .instanceName(InstanceName)
                .status(Status.Pending)
                .userId(subscriptionUser.getId())
                .build();
        InstanceDto instanceResponse = this.instanceService.createNewInstance(newInstance);


        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
    @GetMapping("/get/Instances")
    public ResponseEntity<List<InstanceDto>> getAllInstances(){
        //get all user instances
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO subscriptionUser = this.userService.getUserByEmail(username);

        List<InstanceDto> allInstances = this.instanceService.getAllInstances();
        return new ResponseEntity<>(allInstances,HttpStatus.OK);
    }
    @GetMapping("/get/instances/user")
    public  ResponseEntity<List<InstanceDto>> getUserInstances(@RequestParam long id){
       List<InstanceDto>  userInstances = this.instanceService.getAllUserInstances(id);
       return new ResponseEntity<>(userInstances,HttpStatus.OK);
    }

    @PutMapping("/install/{moduleId}/{instanceId}")
    public ResponseEntity <?>InstallModuleToInstance(@PathVariable int moduleId , int instanceId) throws Exception {
        ModuleDto moduleDto = this.moduleService.getModuleById(moduleId);
        InstanceDto instance = this.instanceService.getInstanceById(instanceId);
        String host = "127.0.0.1";
        InstallModuleDto moduleInfo = new InstallModuleDto();
        moduleInfo.setDb(instance.getInstancedbName());
        moduleInfo.setHost(host);
        moduleInfo.setUser(instance.getAdminUserName());
        moduleInfo.setPassword(instance.getAdminPassword());
        moduleInfo.setModule(moduleDto.getModuleName());
        String  port = getPortFromAddress(instance.getInstanceaddress());
        moduleInfo.setPort(port);
//        if(instance.getStatus().equals(Status.Active)){
//            moduleInfo.setIsActive(true);
//            Request<InstallModuleDto, moduleInstallResponseDTO> request = new Request<>();
//            com.spectrun.spectrum.utils.API.ResponseBody.ResponseBody<moduleInstallResponseDTO> response = request.handleApiCall(moduleInfo,"http://127.0.0.1:5050/api/v1/containerManager/install/running",new TypeReference<ResponseBody<moduleInstallResponseDTO>>() {});
//            return new ResponseEntity<>(response,HttpStatus.OK);
//        }
        Request<InstallModuleDto, moduleInstallResponseDTO> request = new Request<>();
        com.spectrun.spectrum.utils.API.ResponseBody.ResponseBody<moduleInstallResponseDTO> response = request.handleApiCall(moduleInfo,"http://127.0.0.1:5050/api/v1/containerManager/installModule",new TypeReference<ResponseBody<moduleInstallResponseDTO>>() {});
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/install/running")
    public ResponseEntity<?>installToRunningModule(@RequestParam Long id) throws  Exception{
        InstanceDto runningInstance = this.instanceService.getInstanceById(id);
        if(runningInstance != null){
             logger.info("Active instance \n"+runningInstance.toString());

            return  new ResponseEntity<InstanceDto>(runningInstance,HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<InstanceDto>> getByStatus(@PathVariable Status status){
        return ResponseEntity.ok(instanceService.getInstanceByStatus(status));
    }
    @GetMapping("/instance/{id}")
    public ResponseEntity<Boolean> deleteInstanceById(@PathVariable long id){
        Boolean result  = this.instanceService.deleteInstanceById(id);
        return  ResponseEntity.ok(result);
    }


}
