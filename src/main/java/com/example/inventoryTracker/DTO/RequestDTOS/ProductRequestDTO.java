package com.example.inventoryTracker.DTO.RequestDTOS;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private String productName;

    private String unit;

    private String sku;

    private Integer reorderLevel;

    private Double unitCost;
}
