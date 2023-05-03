package com.musala.drones.service.storage;

import com.musala.drones.model.Drone;
import com.musala.drones.model.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findAllByState(DroneState state);
}
