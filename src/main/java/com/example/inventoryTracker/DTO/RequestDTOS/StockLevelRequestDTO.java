package com.example.inventoryTracker.DTO.RequestDTOS;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data
public class StockLevelRequestDTO {

    private Long productId;
    private Long locationId;
    private Integer quantity;
    private TransactionType transactionType;
}
