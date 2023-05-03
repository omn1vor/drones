package com.musala.drones.service.storage;

import com.musala.drones.model.BatteryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryLogRepository extends JpaRepository<BatteryLog, Long> {
}
