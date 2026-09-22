package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class InventoryTransactionIdNotFound extends RuntimeException {
    
    public InventoryTransactionIdNotFound(String message) {
        super(message);
    }

}
