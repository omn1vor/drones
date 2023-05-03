package com.musala.drones.controller;

import com.musala.drones.dto.AddMedicationsRowRequestDto;
import com.musala.drones.dto.DroneBatteryInfoDto;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.LoadedMedicationsRowDto;
import com.musala.drones.service.DroneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
@Validated
@Tag(name = "Drones")
public class DroneController {
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public List<DroneDto> getDrones() {
        return droneService.getDrones();
    }

    @GetMapping("/{serialNumber}")
    public DroneDto getDrone(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return droneService.getDrone(serialNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDrone(@Valid @RequestBody DroneDto droneDto) {
        droneService.registerDrone(droneDto);
    }

    @PostMapping("/{serialNumber}/load")
    public DroneDto loadMedications(@PathVariable @Size(max = 100, min = 1) String serialNumber,
                                    @Valid @RequestBody List<AddMedicationsRowRequestDto> addMedicationsRowRequestDtos) {
        return droneService.loadMedications(serialNumber, addMedicationsRowRequestDtos);
    }

    @GetMapping("/{serialNumber}/medications")
    public List<LoadedMedicationsRowDto> getMedications(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return droneService.getMedications(serialNumber);
    }

    @GetMapping("/available")
    public List<DroneDto> getAvailableDrones() {
        return droneService.getAvailableDrones();
    }

    @GetMapping("/{serialNumber}/battery")
    public DroneBatteryInfoDto getBatteryLevel(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return droneService.getBatteryLevel(serialNumber);
    }
}
