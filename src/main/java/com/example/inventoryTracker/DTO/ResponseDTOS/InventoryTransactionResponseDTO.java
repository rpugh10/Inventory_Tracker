package com.example.inventoryTracker.DTO.ResponseDTOS;

import com.example.inventoryTracker.Entities.TransactionType;

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
    private String transactionDate;
}
