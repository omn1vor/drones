package com.musala.drones.model;

import com.musala.drones.exception.DroneOverloadedException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "drones")
@Getter @Setter @NoArgsConstructor
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
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LoadedMedicationsRow> medications = new ArrayList<>();

    public Drone(String serialNumber, DroneModel model, int weightLimitGrams, int batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimitGrams = weightLimitGrams;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public long getLoadWeight() {
        return medications.stream()
                .mapToInt(LoadedMedicationsRow::getWeight)
                .sum();
    }

    public void addMedications(List<LoadedMedicationsRow> newMedications) {
        long totalWeight = newMedications.stream()
                .mapToInt(LoadedMedicationsRow::getWeight)
                .sum();
        if (totalWeight + getLoadWeight() > getWeightLimitGrams()) {
            throw new DroneOverloadedException(
                    ("Too much weight. Drone can carry %d grams, is already carrying %d grams, and you're trying to add" +
                            "%d grams more")
                            .formatted(getWeightLimitGrams(), getLoadWeight(), totalWeight));
        }
        setState(DroneState.LOADED);
        medications.addAll(newMedications);
    }

    public void addMedications(Medication medication, int quantity) {
        LoadedMedicationsRow row = new LoadedMedicationsRow(this, medication, quantity);
        addMedications(List.of(row));
    }
}
