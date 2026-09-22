package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class InvalidTransactionType extends RuntimeException {
    
    public InvalidTransactionType(String message) {
        super(message);
    }

}
