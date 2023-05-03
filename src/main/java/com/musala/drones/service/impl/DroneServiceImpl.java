package com.musala.drones.service.impl;

import com.musala.drones.dto.*;
import com.musala.drones.exception.DroneAlreadyExistsException;
import com.musala.drones.exception.DroneStateException;
import com.musala.drones.exception.MedicationNotFoundException;
import com.musala.drones.model.Drone;
import com.musala.drones.model.DroneState;
import com.musala.drones.model.LoadedMedicationsRow;
import com.musala.drones.model.Medication;
import com.musala.drones.service.DroneService;
import com.musala.drones.service.storage.DroneRepository;
import com.musala.drones.service.storage.MedicationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;

    public DroneServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository,
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
            throw new DroneAlreadyExistsException(
                    "Drone with ID %s is already registered".formatted(droneDto.getSerialNumber()));
        }

        Drone drone = droneFromDto(droneDto);
        droneRepository.saveAndFlush(drone);
    }

    @Override
    @Transactional
    public DroneDto loadMedications(String serialNumber, List<AddMedicationsRowRequestDto> addMedicationsRowRequestDtos) {
        Drone drone = getDroneBySerialNumber(serialNumber);
        if (drone.getState() != DroneState.IDLE) {
            throw new DroneStateException("Drone %s is not in IDLE state. Loading is not possible");
        }
        drone.setState(DroneState.LOADING);
        droneRepository.save(drone);

        List<LoadedMedicationsRow> medications = addMedicationsRowRequestDtos.stream()
                .map(row -> loadedMedicationRowsFromDto(drone, row))
                .toList();

        drone.addMedications(medications);
        droneRepository.saveAndFlush(drone);
        return droneToDto(drone);
    }

    @Override
    public List<LoadedMedicationsRowDto> getMedications(String serialNumber) {
        Drone drone = getDroneBySerialNumber(serialNumber);
        return drone.getMedications().stream()
                .map(this::loadedMedicationRowToDto)
                .toList();
    }

    @Override
    public List<DroneDto> getAvailableDrones() {
        return droneRepository.findAllByState(DroneState.IDLE).stream()
                .map(this::droneToDto).toList();
    }

    @Override
    public DroneBatteryInfoDto getBatteryLevel(String serialNumber) {
        Drone drone = getDroneBySerialNumber(serialNumber);
        return modelMapper.map(drone, DroneBatteryInfoDto.class);
    }

    private Drone getDroneBySerialNumber(String serialNumber) {
        return droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Drone with ID %s is not registered".formatted(serialNumber)));
    }

    private Drone droneFromDto(DroneDto droneDto) {
        return modelMapper.map(droneDto, Drone.class);
    }

    private DroneDto droneToDto(Drone drone) {
        DroneDto droneDto = modelMapper.map(drone, DroneDto.class);
        droneDto.setMedications(
                drone.getMedications().stream()
                .map(this::loadedMedicationRowToDto)
                        .toList()
        );
        return droneDto;
    }

    private LoadedMedicationsRowDto loadedMedicationRowToDto(LoadedMedicationsRow row) {
        return modelMapper.map(row, LoadedMedicationsRowDto.class);
    }

    private LoadedMedicationsRow loadedMedicationRowsFromDto(Drone drone, AddMedicationsRowRequestDto dto) {
        Medication medication = medicationRepository.findById(dto.getCode())
                .orElseThrow(() -> new MedicationNotFoundException("Medicine with ID %s is not registered"
                        .formatted(dto.getCode())));
        return new LoadedMedicationsRow(drone, medication, dto.getQuantity());
    }
}
