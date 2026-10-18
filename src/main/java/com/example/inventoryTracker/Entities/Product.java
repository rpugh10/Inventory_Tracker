package com.example.inventoryTracker.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ProductName")
    private String productName;
    
    @Column(name = "Unit")
    private String unit;

    @Column(name = "SKU")
    private String sku;

    @Column(name =  "reorderLevel")
    private Integer reorderLevel;

    @Column(name = "UnitCost")
    private Double unitCost;

    public Product() {
    }

    public Product(Long id, String productName, String unit, String sku,
                   Integer reorderLevel, Double unitCost) {
        this.id = id;
        this.productName = productName;
        this.unit = unit;
        this.sku = sku;
        this.reorderLevel = reorderLevel;
        this.unitCost = unitCost;
    }
}
