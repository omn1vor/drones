package com.musala.drones.exception;

public class DroneNotFoundException extends RuntimeException{
    public DroneNotFoundException(String message) {
        super(message);
    }
}
