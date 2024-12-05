package com.spectrun.spectrum.KafkaListeners;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.Enums.Status;
import com.spectrun.spectrum.MessageTemplate.createInstanceResponse;
import com.spectrun.spectrum.controllers.test.kafkaTest;
import com.spectrun.spectrum.services.Implementations.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class instanceCreationResponseListener {
    private InstanceService instanceService;
    Logger logger = Logger.getLogger(instanceCreationResponseListener.class.getName());

    @Autowired
    public instanceCreationResponseListener(InstanceService instanceService) {
        this.instanceService = instanceService;

    }


    @KafkaListener(topics = "create_response",groupId = "response_group",containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consume(createInstanceResponse response){
        logger.info(response.toString());
        InstanceDto instanceDto;
        LocalDateTime today = LocalDateTime.now();
            instanceDto = InstanceDto.builder().
                    instanceName(response.getInstanceName())
                    .status((response.getInstanceCreationStatus() ? Status.Active : Status.Failed))
                    .instancedbaddress(response.getInstanceDbAddress())
                    .instancedbName(response.getInstanceDbName())
                    .instanceaddress(response.getInstanceAddress())
                    .configurationFileLocation(response.getConfigurationFileLocation())
                    .supportedVersion("17")
                   .adminUserName(response.getAdminUserName())
                   .adminPassword(response.getAdminPassword())
                    .createdOn(today)
                    .updatedOn(today)
                    .build();

                InstanceDto newinstance =  instanceService.createNewInstance(instanceDto);




    }

}
