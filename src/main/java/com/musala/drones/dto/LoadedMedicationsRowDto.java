package com.musala.drones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoadedMedicationsRowDto {
    MedicationDto medication;

    int quantity;
}
