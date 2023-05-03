package com.musala.drones.controller;

import com.musala.drones.dto.*;
import com.musala.drones.model.LoadedMedicationsRow;
import com.musala.drones.service.DronesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
@Validated
public class DroneController {
    DronesService dronesService;

    public DroneController(DronesService dronesService) {
        this.dronesService = dronesService;
    }

    @GetMapping
    public List<DroneDto> getDrones() {
        return dronesService.getDrones();
    }

    @GetMapping("/{serialNumber}")
    public DroneDto getDrone(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return dronesService.getDrone(serialNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDrone(@Valid @RequestBody DroneDto droneDto) {
        dronesService.registerDrone(droneDto);
    }

    @PostMapping("/{serialNumber}/load")
    public DroneDto loadMedications(@PathVariable @Size(max = 100, min = 1) String serialNumber,
                                    @Valid @RequestBody List<AddMedicationsRowRequestDto> addMedicationsRowRequestDtos) {
        return dronesService.loadMedications(serialNumber, addMedicationsRowRequestDtos);
    }

    @GetMapping("/{serialNumber}/medications")
    public List<LoadedMedicationsRowDto> getMedications(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return dronesService.getMedications(serialNumber);
    }

    @GetMapping("/available")
    public List<DroneDto> getAvailableDrones() {
        return dronesService.getAvailableDrones();
    }

    @GetMapping("/{serialNumber}/battery")
    public DroneBatteryInfoDto getBatteryLevel(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return dronesService.getBatteryLevel(serialNumber);
    }
}
