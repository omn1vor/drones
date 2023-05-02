package com.musala.drones.service;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.LoadedMedicationsRowDTO;
import com.musala.drones.model.Drone;

import java.util.List;

public interface DronesService {
    List<DroneDto> getDrones();

    DroneDto getDrone(String serialNumber);

    void registerDrone(DroneDto droneDto);

    DroneDto loadMedications(String serialNumber, List<LoadedMedicationsRowDTO> loadedMedicationsRowDTOS);
}
