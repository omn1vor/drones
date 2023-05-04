package com.musala.drones.service.impl;

import com.musala.drones.model.BatteryLog;
import com.musala.drones.model.Drone;
import com.musala.drones.service.DroneBatteryService;
import com.musala.drones.service.storage.DroneRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Profile("kafka")
public class DroneBatteryServiceKafkaImpl implements DroneBatteryService {

    private final DroneRepository droneRepository;
    private final KafkaTemplate<String, BatteryLog> kafkaTemplate;

    public DroneBatteryServiceKafkaImpl(DroneRepository droneRepository, KafkaTemplate<String, BatteryLog> kafkaTemplate) {
        this.droneRepository = droneRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Scheduled(fixedRateString = "${drone.battery.log.rate}")
    public void logBatteryLevels() {
        for (Drone drone: droneRepository.findAll()) {
            BatteryLog log = new BatteryLog(drone.getSerialNumber(), drone.getBatteryCapacity());
            kafkaTemplate.sendDefault(log);
        }
    }
}
