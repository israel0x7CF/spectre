package com.spectrun.spectrum.config.kafka;

import com.spectrun.spectrum.utils.exceptions.KafkaConnectionFailed;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic createInstance(){
        try{
            return TopicBuilder
                    .name("create_instance")
                    .build();
        }
        catch (Exception e){
            throw new KafkaConnectionFailed("Connection To Kafa Server Failed");
        }

    }
    @Bean
    public NewTopic createInstanceResponse(){
        return TopicBuilder
                    .name("create_response")
                .build();
    }
    @Bean
    public NewTopic initializeServer(){
        return TopicBuilder
                .name("initialize_server")
                .build();
    }


}
