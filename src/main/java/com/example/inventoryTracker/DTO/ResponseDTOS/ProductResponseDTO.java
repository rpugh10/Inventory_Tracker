package com.example.inventoryTracker.DTO.ResponseDTOS;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;

    private String productName;

    private String unit;

    private String sku;

    private Integer reorderLevel;

    private Double unitCost;
}
