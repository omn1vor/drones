package com.musala.drones.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConfigurationProperties(prefix = "drone.battery")
@EnableScheduling
@Getter @Setter
public class DroneBatteryConfig {
    private int minCapacityForLoading;
}
