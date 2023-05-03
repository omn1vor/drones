package com.musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedicationDto {
    @Pattern(regexp = "[A-Z0-9_]+") @Size(max = 100, min = 1)
    String code;

    @Pattern(regexp = "[\\w-]+") @NotNull
    String name;

    @Min(1)
    int weightGrams;

    String imageUrl;
}
