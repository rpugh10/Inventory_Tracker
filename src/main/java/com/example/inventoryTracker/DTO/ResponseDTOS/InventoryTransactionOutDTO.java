package com.example.inventoryTracker.DTO.ResponseDTOS;

import org.springframework.data.auditing.CurrentDateTimeProvider;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data 
public class InventoryTransactionOutDTO {

    private Long productId;
    private Long locationId;
    private String productName;
    private Integer quantity;
    private TransactionType transactionTypeEnum;
    private CurrentDateTimeProvider transactionDate;
}
