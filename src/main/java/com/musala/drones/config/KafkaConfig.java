package com.musala.drones.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drones.model.BatteryLog;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@Profile("kafka")
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    public KafkaConfig(KafkaProperties kafkaProperties,
                       ObjectMapper objectMapper) {

        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public NewTopic BatteryLogsTopic() {
        return TopicBuilder
                .name("drones.battery-logs")
                .partitions(3)
                .replicas(2)
                .build();
    }

    @Bean
    public ProducerFactory<String, BatteryLog> batteryLogsProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                kafkaProperties.buildProducerProperties(),
                new StringSerializer(),
                new JsonSerializer<>(objectMapper)
        );
    }

    @Bean
    public KafkaTemplate<String, BatteryLog> kafkaTemplate() {
        KafkaTemplate<String, BatteryLog> kafkaTemplate = new KafkaTemplate<>(batteryLogsProducerFactory());
        kafkaTemplate.setDefaultTopic("drones.battery-logs");
        return kafkaTemplate;
    }

}
