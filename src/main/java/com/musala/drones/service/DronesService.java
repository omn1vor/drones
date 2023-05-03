package com.musala.drones.service;

import com.musala.drones.dto.*;

import java.util.List;

public interface DronesService {
    List<DroneDto> getDrones();

    DroneDto getDrone(String serialNumber);

    void registerDrone(DroneDto droneDto);

    DroneDto loadMedications(String serialNumber, List<AddMedicationsRowRequestDto> addMedicationsRowRequestDtos);

    List<LoadedMedicationsRowDto> getMedications(String serialNumber);

    List<DroneDto> getAvailableDrones();

    DroneBatteryInfoDto getBatteryLevel(String serialNumber);
}
