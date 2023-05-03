package com.musala.drones.service;

import com.musala.drones.dto.MedicationDto;

import java.util.List;

public interface MedicationService {
    void addMedication(MedicationDto medicationDto);

    List<MedicationDto> getMedications();
}
