package com.example.inventoryTracker.DTO.ResponseDTOS;

import java.time.LocalDateTime;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data
public class InventoryTransactionResponseDTO {

    private Long id;
    private Long appUserId;
    private Long supplierId;
    private Long locationId;
    private Long productId;
    private String productName;
    private String note;
    private Integer quantity;
    private TransactionType transactionTypeEnum;
}
