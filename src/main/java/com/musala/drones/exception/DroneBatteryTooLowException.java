package com.musala.drones.exception;

public class DroneBatteryTooLowException extends RuntimeException {
    public DroneBatteryTooLowException(String message) {
        super(message);
    }
}
