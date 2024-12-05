package com.spectrun.spectrum.config.kafka;

import com.spectrun.spectrum.MessageTemplate.createInstanceResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class kafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, createInstanceResponse> createInstanceConsumer() {
        Map<String, Object> props = new HashMap<>();

        // Broker address
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        // Group ID
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "response_group");

        // Key deserializer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Configure JsonDeserializer
        JsonDeserializer<createInstanceResponse> jsonDeserializer = new JsonDeserializer<>(createInstanceResponse.class);
        jsonDeserializer.addTrustedPackages("*"); // Allows all trusted packages
        jsonDeserializer.setRemoveTypeHeaders(false); // Don't expect Spring-specific headers
        jsonDeserializer.setUseTypeMapperForKey(false); // Allow raw JSON without type info

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, createInstanceResponse> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, createInstanceResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createInstanceConsumer());

        // Error handler for deserialization issues


        return factory;
    }
}

