package com.musala.drones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AddMedicationsRowRequestDto {
    @Pattern(regexp = "[A-Z0-9_]+") @Size(max = 100, min = 1)
    String code;

    @Min(1)
    int quantity;
}
