package com.musala.drones.config;

import com.musala.drones.model.Drone;
import com.musala.drones.model.DroneModel;
import com.musala.drones.model.DroneState;
import com.musala.drones.model.Medication;
import com.musala.drones.service.storage.DroneRepository;
import com.musala.drones.service.storage.MedicationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestData implements ApplicationRunner {

    MedicationRepository medicationRepository;
    DroneRepository droneRepository;
    Medication aspirin;
    Medication codeine;
    Medication amoxicillin;

    public TestData(MedicationRepository medicationRepository, DroneRepository droneRepository) {
        this.medicationRepository = medicationRepository;
        this.droneRepository = droneRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        populateMedicine();
        populateDrones();
    }

    private void populateMedicine() {
        aspirin = new Medication("ASPIRIN", "Aspirin", 20, "url");
        codeine = new Medication("CD_1", "Codeine", 15, "url");
        amoxicillin = new Medication("AM_01", "Amoxicillin", 25, "url");

        List<Medication> medications = List.of(
                aspirin,
                codeine,
                amoxicillin
        );

        medications.forEach(medicationRepository::save);
    }

    private void populateDrones() {

        List<Drone> drones = List.of(
                new Drone("HV-112312", DroneModel.HEAVYWEIGHT, 400, 100,
                        DroneState.IDLE),
                new Drone("MD-001", DroneModel.MIDDLEWEIGHT, 300, 75,
                        DroneState.IDLE),
                new Drone("MD-001a", DroneModel.MIDDLEWEIGHT, 300, 75,
                        DroneState.IDLE),
                new Drone("SM-001", DroneModel.LIGHTWEIGHT, 100, 25,
                        DroneState.IDLE)
        );

        drones.get(0).addMedications(aspirin, 2);
        drones.get(0).addMedications(codeine, 2);
        drones.get(0).addMedications(amoxicillin, 2);
        drones.get(0).setState(DroneState.DELIVERING);

        drones.get(2).addMedications(codeine, 3);
        drones.get(2).addMedications(amoxicillin, 2);

        drones.get(3).addMedications(aspirin, 3);
        drones.get(3).setState(DroneState.IDLE);

        drones.forEach(droneRepository::save);
    }
}
