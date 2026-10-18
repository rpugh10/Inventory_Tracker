package com.example.inventoryTracker.DTO.ResponseDTOS;

import java.time.LocalDateTime;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data 
public class InventoryTransactionOutDTO {

    private Long id;
    private Long userId;
    private Long supplierId;
    private Long productId;
    private Long locationId;
    private String productName;
    private String note;
    private Integer quantity;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
}
