package com.example.inventoryTracker.DTO.ResponseDTOS;

import com.example.inventoryTracker.Entities.StockLevelId;
import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data
public class StockLevelResponseDTO {

    private StockLevelId id;

    private Long productId;

    private Long locationId;

    private Integer quantity;

    private TransactionType transactionType;
}
