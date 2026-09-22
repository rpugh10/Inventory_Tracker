package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class InvalidQuantity extends RuntimeException {
    
    public InvalidQuantity(String message) {
        super(message);
    }

}
