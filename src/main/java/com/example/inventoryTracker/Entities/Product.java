package com.example.inventoryTracker.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
}
