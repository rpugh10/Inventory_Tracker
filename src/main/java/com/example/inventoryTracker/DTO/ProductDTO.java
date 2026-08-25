package com.example.inventoryTracker.DTO;

import lombok.Data;

@Data
public class ProductDTO {

    private String productName;

    private String unit;

    private String sku;

    private Integer reorderLevel;

    private Double unitCost;
}
