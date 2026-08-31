package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class DuplicateUser extends RuntimeException {
    
    public DuplicateUser(String message) {
        super(message);
    }


}
