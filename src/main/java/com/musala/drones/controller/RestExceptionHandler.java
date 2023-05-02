package com.musala.drones.controller;

import com.musala.drones.exception.DroneNotFoundException;
import com.musala.drones.exception.DroneOverloadedException;
import com.musala.drones.exception.ErrorDetails;
import com.musala.drones.exception.MedicationNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({DroneNotFoundException.class, MedicationNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class, DroneOverloadedException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
