package com.musala.drones.service.impl;

import com.musala.drones.dto.MedicationDto;
import com.musala.drones.model.Medication;
import com.musala.drones.service.MedicationService;
import com.musala.drones.service.storage.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;

    public MedicationServiceImpl(MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addMedication(MedicationDto medicationDto) {
        Medication medication = modelMapper.map(medicationDto, Medication.class);
        medicationRepository.saveAndFlush(medication);
    }

    @Override
    public List<MedicationDto> getMedications() {
        return medicationRepository.findAll().stream()
                .map(medication -> modelMapper.map(medication, MedicationDto.class))
                .toList();
    }
}
