package com.musala.drones.controller;

import com.musala.drones.dto.MedicationDto;
import com.musala.drones.service.MedicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medications")
@Validated
@Tag(name = "Medications")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    List<MedicationDto> getMedications() {
        return medicationService.getMedications();
    }

    @PostMapping
    void addMedication(@Valid @RequestBody MedicationDto medicationDto) {
        medicationService.addMedication(medicationDto);
    }
}
