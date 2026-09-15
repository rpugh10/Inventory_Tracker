package com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data 
public class InventoryTransactionInDTO {
    private Long supplierId;
    private Long locationId;
    private Long productId;
    private Integer quantity;
    private TransactionType transactionTypeEnum;
}
