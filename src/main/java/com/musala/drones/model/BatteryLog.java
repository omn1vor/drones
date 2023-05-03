package com.musala.drones.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Entity
@Profile("!kafka")
@Getter @Setter @NoArgsConstructor
public class BatteryLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    LocalDateTime timestamp;
    String serialNumber;
    int batteryCapacity;

    public BatteryLog(String serialNumber, int batteryCapacity) {
        this.serialNumber = serialNumber;
        this.batteryCapacity = batteryCapacity;
        timestamp = LocalDateTime.now();
    }
}
