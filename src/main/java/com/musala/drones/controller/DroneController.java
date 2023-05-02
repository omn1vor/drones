package com.musala.drones.controller;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.LoadedMedicationsRowDTO;
import com.musala.drones.service.DronesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Validated
public class DroneController {
    DronesService dronesService;

    public DroneController(DronesService dronesService) {
        this.dronesService = dronesService;
    }

    @GetMapping("drones")
    public List<DroneDto> getDrones() {
        return dronesService.getDrones();
    }

    @GetMapping("drones/{serialNumber}")
    public DroneDto getDrone(@PathVariable @Size(max = 100, min = 1) String serialNumber) {
        return dronesService.getDrone(serialNumber);
    }

    @PostMapping("drones")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDrone(@Valid @RequestBody DroneDto droneDto) {
        dronesService.registerDrone(droneDto);
    }

    @PostMapping("drones/{serialNumber}/load")
    public DroneDto loadMedications(@PathVariable @Size(max = 100, min = 1) String serialNumber,
                                    @Valid @RequestBody List<LoadedMedicationsRowDTO> loadedMedicationsRowDTOS) {
        return dronesService.loadMedications(serialNumber, loadedMedicationsRowDTOS);
    }
}
