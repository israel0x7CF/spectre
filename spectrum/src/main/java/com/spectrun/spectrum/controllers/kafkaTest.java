package com.spectrun.spectrum.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spectrun.spectrum.MessageTemplate.createInstanceTemplate;
import com.spectrun.spectrum.utils.API.Request;
import com.spectrun.spectrum.utils.API.RequestDTO.InstallModuleDto;
import com.spectrun.spectrum.utils.API.ResponseBody.ResponseBody;
import com.spectrun.spectrum.utils.API.ResponseDTO.moduleInstallResponseDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/test/")
public class kafkaTest {
    static record messageComp(String message){

    }
    Logger logger = Logger.getLogger(kafkaTest.class.getName());
    private KafkaTemplate<String,String> kafkaTemplate;
    private KafkaTemplate<String, createInstanceTemplate> createInstance;

    public kafkaTest(KafkaTemplate<String, String> kafkaTemplate,KafkaTemplate<String, createInstanceTemplate> createInstanceTemplate) {
        this.createInstance = createInstanceTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/testMessage")
    public String sendTestMessage(@RequestBody messageComp m){
        kafkaTemplate.send("create_instance",m.message);
        return "sent";
    }
    @PostMapping("/testConfigMessage")
    public String sendTestConfig(@RequestBody createInstanceTemplate m){
        createInstance.send("create_instance",m);
        return "sent";
    }
    @PostMapping("/request")
    public String requestModuleTest(){
        InstallModuleDto moduleInfo = new InstallModuleDto();
        moduleInfo.setDb("test");
        moduleInfo.setHost("mad");
        moduleInfo.setUser("mark");
        moduleInfo.setPassword("123");
        moduleInfo.setPort("442");
        moduleInfo.setModule("crm");

        Request<InstallModuleDto, moduleInstallResponseDTO> request = new Request<>();
        ResponseBody<moduleInstallResponseDTO> response = request.handleApiCall(moduleInfo,"http://127.0.0.1:5050/api/v1/containerManager/installModule",new TypeReference<ResponseBody<moduleInstallResponseDTO>>() {});
        logger.info("response for api"+response.getData().toString());
        return "request sent";
    }


}


