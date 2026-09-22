package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class LocationNotFound extends RuntimeException {
    
    public LocationNotFound(String message) {
        super(message);
    }

}
