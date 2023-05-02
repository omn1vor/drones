package com.musala.drones.service.impl;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.LoadedMedicationsRowDTO;
import com.musala.drones.dto.MedicationDto;
import com.musala.drones.exception.DroneOverloadedException;
import com.musala.drones.exception.MedicationNotFoundException;
import com.musala.drones.model.Drone;
import com.musala.drones.model.LoadedMedicationsRow;
import com.musala.drones.model.Medication;
import com.musala.drones.service.DronesService;
import com.musala.drones.service.storage.DroneRepository;
import com.musala.drones.service.storage.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DronesServiceImpl implements DronesService {

    DroneRepository droneRepository;
    MedicationRepository medicationRepository;
    ModelMapper modelMapper;

    public DronesServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository,
                             ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DroneDto> getDrones() {
        return droneRepository.findAll(Pageable.ofSize(50))
                .map(this::droneToDto).toList();
    }

    @Override
    public DroneDto getDrone(String serialNumber) {
        return droneToDto(getDroneBySerialNumber(serialNumber));
    }

    @Override
    public void registerDrone(DroneDto droneDto) {
        if (droneRepository.existsById(droneDto.getSerialNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Drone with ID %s is already registered".formatted(droneDto.getSerialNumber()));
        }

        Drone drone = droneFromDto(droneDto);
        droneRepository.saveAndFlush(drone);
    }

    @Override
    public DroneDto loadMedications(String serialNumber, List<LoadedMedicationsRowDTO> loadedMedicationsRowDTOS) {
        Drone drone = getDroneBySerialNumber(serialNumber);

        List<LoadedMedicationsRow> medications = loadedMedicationsRowDTOS.stream()
                .map(row -> loadedMedicationRowsFromDto(drone, row))
                .toList();

        if (!drone.addMedications(medications)) {
            throw new DroneOverloadedException(
                    ("Too much weight. Drone can carry %d grams, is already carrying %d grams, and you're trying to add" +
                            "%d grams more")
                            .formatted(drone.getWeightLimitGrams(), drone.getLoadWeight(), getLoadWeight(medications)));
        }
        droneRepository.saveAndFlush(drone);
        return droneToDto(drone);
    }

    private Drone getDroneBySerialNumber(String serialNumber) {
        return droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Drone with ID %s is not registered".formatted(serialNumber)));
    }

    private int getLoadWeight(List<LoadedMedicationsRow> rows) {
        return rows.stream()
                .mapToInt(LoadedMedicationsRow::getWeight)
                .sum();
    }

    private Drone droneFromDto(DroneDto droneDto) {
        return modelMapper.map(droneDto, Drone.class);
    }

    private DroneDto droneToDto(Drone drone) {
        return modelMapper.map(drone, DroneDto.class);
    }

    private Medication medicationFromDto(MedicationDto medicationDto) {
        return modelMapper.map(medicationDto, Medication.class);
    }

    private LoadedMedicationsRow loadedMedicationRowsFromDto(Drone drone, LoadedMedicationsRowDTO dto) {
        Medication medication = medicationRepository.findById(dto.getCode())
                .orElseThrow(() -> new MedicationNotFoundException("Medicine with ID %s is not registered"
                        .formatted(dto.getCode())));
        return new LoadedMedicationsRow(drone, medication, dto.getQuantity());
    }
}
