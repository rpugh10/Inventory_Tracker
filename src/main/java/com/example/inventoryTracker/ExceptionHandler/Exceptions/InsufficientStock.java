package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class InsufficientStock extends RuntimeException {
    
    public InsufficientStock(String message) {
        super(message);
    }

}
