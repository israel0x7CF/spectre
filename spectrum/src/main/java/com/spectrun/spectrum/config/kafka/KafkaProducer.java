package com.spectrun.spectrum.config.kafka;

import com.spectrun.spectrum.MessageTemplate.createInstanceTemplate;
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
public class KafkaProducer {
    @Value("${spring.kafka.bootstrap-server}")
    private String kafkaSession;

    public Map<String,Object> kafaConfig(){
        Map<String,Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaSession);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return  properties;
    }
    public HashMap<String,Object> instanceConfigurationConfig(){
        HashMap<String,Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaSession);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return  properties;
    }
    @Bean
    public ProducerFactory<String,String> producerFactory(){
        return  new DefaultKafkaProducerFactory<>(this.kafaConfig());
    }
    @Bean
    public ProducerFactory<String, createInstanceTemplate> instanceMessageProducerFactory(){
        return  new DefaultKafkaProducerFactory<>(this.instanceConfigurationConfig());
    }
    @Bean
    public KafkaTemplate <String,String> kafkaTemplate (){
        return  new KafkaTemplate<>(producerFactory());

    }
    @Bean
    public KafkaTemplate <String,createInstanceTemplate> instanceTemplate(){
        return  new KafkaTemplate<>(instanceMessageProducerFactory());
    }


}
