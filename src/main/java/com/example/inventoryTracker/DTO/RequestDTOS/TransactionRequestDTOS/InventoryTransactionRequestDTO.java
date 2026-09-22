package com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InventoryTransactionRequestDTO {

    @NotNull 
    private Long AppUserId;

    
    private Long supplierId;

    @NotNull 
    private Long locationId;

    @NotNull 
    private Long productId;

    
    private String note;

    @NotNull 
    @Positive 
    private Integer quantity;

    @NotNull 
    private TransactionType transactionTypeEnum;
}
