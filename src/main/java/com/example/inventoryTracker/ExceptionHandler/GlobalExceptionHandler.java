package com.example.inventoryTracker.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.inventoryTracker.DTO.ResponseDTOS.ApiErrorResponse;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.DuplicateUser;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InsufficientStock;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidQuantity;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidTransactionType;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InventoryTransactionIdNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.LocationNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.ProductNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.StockLevelIdNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.SupplierNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ UserNotFoundException.class, ProductNotFound.class, LocationNotFound.class,
            SupplierNotFound.class, InventoryTransactionIdNotFound.class, StockLevelIdNotFound.class })
    public ResponseEntity<ApiErrorResponse> handleNotFound(RuntimeException exception) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicateUser.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(DuplicateUser exception) {
        return response(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({ InvalidQuantity.class, InvalidTransactionType.class, InsufficientStock.class })
    public ResponseEntity<ApiErrorResponse> handleInvalidMovement(RuntimeException exception) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream().findFirst()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .orElse("Request validation failed");
        return response(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ApiErrorResponse> response(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApiErrorResponse.of(status.value(), message));
    }
}
