package com.example.inventoryTracker.ExceptionHandler.Exceptions;

public class ProductNotFound extends RuntimeException {
    
    public ProductNotFound(String message) {
        super(message);
    }

}
