package com.musala.drones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Drone {
    @Id @Column(length = 100) @Size(max = 100, min = 1)
    String serialNumber;
    @Enumerated(EnumType.STRING) @NotNull
    DroneModel model;
    @Max(500) @Min(0)
    int weightLimitGrams;
    @Max(100) @Min(0)
    int batteryCapacity;
    @Enumerated(EnumType.STRING)
    DroneState state;
}
