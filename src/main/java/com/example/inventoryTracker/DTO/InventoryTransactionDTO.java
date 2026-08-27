package com.example.inventoryTracker.DTO;



import java.time.LocalDateTime;

import com.example.inventoryTracker.Entities.TransactionType;

import lombok.Data;

@Data
public class InventoryTransactionDTO {
    
    private Long id;
    private Long appUserId;
    private Long supplierId;
    private Long locationId;
    private Long productId;
    private String productName;
    private String note;
    private Integer quantity;
    private TransactionType transactionTypeEnum;
    private LocalDateTime transactionDate;
}
