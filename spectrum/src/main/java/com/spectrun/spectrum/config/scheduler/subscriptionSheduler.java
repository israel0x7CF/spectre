package com.spectrun.spectrum.config.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class subscriptionSheduler {
    private  static Logger log = LoggerFactory.getLogger(subscriptionSheduler.class);
    @Scheduled(fixedRate = 86_400_000)
    public void sendScheduledEmails (){
        log.info("sending Emails");
    }
}
