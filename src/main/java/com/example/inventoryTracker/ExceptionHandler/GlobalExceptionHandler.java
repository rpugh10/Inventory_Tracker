package com.example.inventoryTracker.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.inventoryTracker.ExceptionHandler.Exceptions.DuplicateUser;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidQuantity;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidTransactionType;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InventoryTransactionIdNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.LocationNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.ProductNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.StockLevelIdNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.SupplierNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.UserNotFoundException;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InsufficientStock;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateUser.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DuplicateUser ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler (LocationNotFound.class)
    public ResponseEntity<String> handleLocationNotFound(LocationNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler (SupplierNotFound.class)
    public ResponseEntity<String> handleSupplierNotFound(SupplierNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler (InvalidQuantity.class)
    public ResponseEntity<String> handleInvalidQuantity(InvalidQuantity ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler (InsufficientStock.class)
    public ResponseEntity<String> handleInsufficientStock(InsufficientStock ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler (InventoryTransactionIdNotFound.class)
    public ResponseEntity<String> handleInventoryTransactionIdNotFound(InventoryTransactionIdNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler (StockLevelIdNotFound.class)
    public ResponseEntity<String> handleStockLevelIdNotFound(StockLevelIdNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler (InvalidTransactionType.class)
    public ResponseEntity<String> handleInvalidTransactionType(InvalidTransactionType ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}