package com.example.inventoryTracker.DTO.ResponseDTOS;

import java.time.LocalDateTime;

import org.springframework.data.auditing.CurrentDateTimeProvider;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import lombok.Data;

@Data 
public class InventoryTransactionOutDTO {

    private Long userId;
    private Long productId;
    private Long locationId;
    private String productName;
    private String note;
    private Integer quantity;
    private TransactionType transactionTypeEnum;
    private LocalDateTime transactionDate;
}
