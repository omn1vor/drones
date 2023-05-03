package com.musala.drones.exception;

public class DroneAlreadyExistsException extends RuntimeException {
    public DroneAlreadyExistsException(String message) {
        super(message);
    }
}
