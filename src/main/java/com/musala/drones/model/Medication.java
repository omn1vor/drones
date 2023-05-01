package com.musala.drones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Medication {
    @Id @Pattern(regexp = "[A-Z0-9_]+") @Size(max = 100, min = 1) @Column(length = 100)
    String code;
    @Pattern(regexp = "[\\w-]+") @NotNull
    String name;
    int weightGrams;
    String image;
}
