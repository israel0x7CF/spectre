package com.spectrun.spectrum.KafkaListeners;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.MessageTemplate.createInstanceResponse;
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

        LocalDateTime today = LocalDateTime.now();

        InstanceDto newinstance =  instanceService.updateInstance(response);




    }

}
