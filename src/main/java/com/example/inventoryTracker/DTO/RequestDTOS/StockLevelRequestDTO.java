package com.example.inventoryTracker.DTO.RequestDTOS;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockLevelRequestDTO {

    @NotNull 
    private Long productId;

    @NotNull 
    private Long locationId;

    @NotNull 
    @Positive 
    private Integer quantity;

    @NotNull 
    private TransactionType transactionType;
}
