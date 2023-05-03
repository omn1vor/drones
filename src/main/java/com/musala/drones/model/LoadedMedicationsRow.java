package com.musala.drones.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "loaded_medications_row")
@Getter @Setter @NoArgsConstructor
public class LoadedMedicationsRow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Drone drone;

    @ManyToOne(fetch = FetchType.LAZY)
    Medication medication;

    int quantity;

    public LoadedMedicationsRow(Drone drone, Medication medication, int quantity) {
        this.drone = drone;
        this.medication = medication;
        this.quantity = quantity;
    }

    public int getWeight() {
        return medication.getWeightGrams() * quantity;
    }
}
