package com.musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musala.drones.model.DroneModel;
import com.musala.drones.model.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class DroneDto {
    @Size(max = 100, min = 1)
    String serialNumber;

    @NotNull
    DroneModel model;

    @Max(500) @Min(0)
    int weightLimitGrams;

    @Max(100) @Min(0)
    int batteryCapacity;

    DroneState state;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<MedicationDto> medications = new ArrayList<>();

    public DroneDto(String serialNumber, DroneModel model, int weightLimitGrams, int batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimitGrams = weightLimitGrams;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }
}
