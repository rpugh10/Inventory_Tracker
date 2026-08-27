package com.example.inventoryTracker.DTO;

import com.example.inventoryTracker.Entities.TransactionType;

import lombok.Data;

@Data
public class StockLevelDTO {

    private Long productId;
    private Long locationId;
    private Integer quantity;
    private TransactionType transactionType;
}
