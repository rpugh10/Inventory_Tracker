package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class StockLevelIdNotFound extends RuntimeException {
    
    public StockLevelIdNotFound(String message) {
        super(message);
    }

}
