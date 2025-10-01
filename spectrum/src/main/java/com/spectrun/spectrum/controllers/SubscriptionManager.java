package com.spectrun.spectrum.controllers;


import com.spectrun.spectrum.DTO.SubscriptionDTO;
import com.spectrun.spectrum.services.Implementations.SubscriptionService;
import com.spectrun.spectrum.utils.exceptions.PlanNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionManager {
    private KafkaTemplate<String,String> kafkaTemplate;
    private SubscriptionService subscriptionService;
    private static final Logger log = LoggerFactory.getLogger(SubscriptionManager.class);

    public SubscriptionManager(KafkaTemplate<String, String> kafkaTemplate, SubscriptionService subscriptionService) {
        this.kafkaTemplate = kafkaTemplate;
        this.subscriptionService = subscriptionService;
    }


    @PostMapping("/register/subscription")
    public ResponseEntity<SubscriptionDTO> addSubscription(@RequestBody SubscriptionDTO subscriptionDTO){
        try {
            SubscriptionDTO subscriptionDTO1 = this.subscriptionService.createSubscription(subscriptionDTO);

        }catch (PlanNotFoundException planNotFoundException){
            log.info("Exception");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

        return  new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
    }
}
