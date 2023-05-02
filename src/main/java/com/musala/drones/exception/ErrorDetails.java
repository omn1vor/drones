package com.musala.drones.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String request;

    public ErrorDetails(String message, String request) {
        this.message = message;
        this.request = request;
        timestamp = LocalDateTime.now();
    }
}
