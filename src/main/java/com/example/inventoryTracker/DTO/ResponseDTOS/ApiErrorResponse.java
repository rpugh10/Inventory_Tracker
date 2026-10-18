package com.example.inventoryTracker.DTO.ResponseDTOS;

import java.time.LocalDateTime;

public record ApiErrorResponse(LocalDateTime timestamp, int status, String message) {
    public static ApiErrorResponse of(int status, String message) {
        return new ApiErrorResponse(LocalDateTime.now(), status, message);
    }
}
