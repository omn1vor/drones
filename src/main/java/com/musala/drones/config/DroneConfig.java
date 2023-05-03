package com.musala.drones.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "drone")
@Getter @Setter
public class DroneConfig {
    private int minBatteryCapacityForLoading;
}
