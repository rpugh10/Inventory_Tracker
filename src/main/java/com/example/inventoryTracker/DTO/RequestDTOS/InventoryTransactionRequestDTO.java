package com.example.inventoryTracker.DTO.RequestDTOS;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data
public class InventoryTransactionRequestDTO {

    private Long appUserId;
    private Long supplierId;
    private Long locationId;
    private Long productId;
    private String note;
    private Integer quantity;
    private TransactionType transactionTypeEnum;
}
