package com.example.inventoryTracker.Entities;



import com.example.inventoryTracker.Entities.Enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockLevel {
    
    @EmbeddedId
    private StockLevelId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId", foreignKey = @jakarta.persistence.ForeignKey(name = "productId"))
    private Product product;

    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "locationId", foreignKey = @jakarta.persistence.ForeignKey(name = "locationId"))
    private Location location;

    @Column(name = "Quantity")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "TransactionType")
    private TransactionType transactionType;

}
