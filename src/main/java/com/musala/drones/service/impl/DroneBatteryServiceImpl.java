package com.musala.drones.service.impl;

import com.musala.drones.model.BatteryLog;
import com.musala.drones.model.Drone;
import com.musala.drones.service.DroneBatteryService;
import com.musala.drones.service.storage.BatteryLogRepository;
import com.musala.drones.service.storage.DroneRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DroneBatteryServiceImpl implements DroneBatteryService {

    private final DroneRepository droneRepository;
    private final BatteryLogRepository batteryLogRepository;

    public DroneBatteryServiceImpl(DroneRepository droneRepository, BatteryLogRepository batteryLogRepository) {
        this.droneRepository = droneRepository;
        this.batteryLogRepository = batteryLogRepository;
    }

    @Override
    @Scheduled(fixedRateString = "${drone.battery.log.rate}")
    public void logBatteryLevels() {
        for (Drone drone: droneRepository.findAll()) {
            BatteryLog log = new BatteryLog(drone.getSerialNumber(), drone.getBatteryCapacity());
            batteryLogRepository.save(log);
        }
    }
}
