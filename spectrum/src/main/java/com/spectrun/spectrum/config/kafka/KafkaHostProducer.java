package com.spectrun.spectrum.config.kafka;

import com.spectrun.spectrum.MessageTemplate.initializeServerTemplate;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaHostProducer {
    @Value("${spring.kafka.bootstrap-server}")
    private String kafkaSession;

    public Map<String,Object> kafkaHostConfig(){
        Map<String,Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaSession);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return  properties;
    }
    public HashMap<String,Object> hostConfigurationConfig(){
        HashMap<String,Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaSession);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return  properties;
    }
    @Bean
    public ProducerFactory<String,String> HostProducerFactory(){
        return  new DefaultKafkaProducerFactory<>(this.kafkaHostConfig());
    }
    @Bean
    public ProducerFactory<String, initializeServerTemplate> hostMessageProducerFactory(){
        return  new DefaultKafkaProducerFactory<>(this.hostConfigurationConfig());
    }
    @Bean
    public KafkaTemplate<String,String> kafkaHostTemplate (){
        return  new KafkaTemplate<>(HostProducerFactory());

    }
    @Bean
    public KafkaTemplate <String,initializeServerTemplate> hostTemplate(){
        return  new KafkaTemplate<>(hostMessageProducerFactory());
    }


}
