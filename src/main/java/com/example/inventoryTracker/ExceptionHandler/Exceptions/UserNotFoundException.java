package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }

}
