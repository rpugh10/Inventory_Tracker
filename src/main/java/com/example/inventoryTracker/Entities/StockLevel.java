package com.example.inventoryTracker.Entities;



import java.time.LocalDateTime;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class StockLevel {

    public StockLevel() {
    }

    public StockLevel(Long version, StockLevelId id, Product product, Location location,
                      Integer quantity, LocalDateTime lastUpdated, TransactionType transactionType) {
        this.version = version;
        this.id = id;
        this.product = product;
        this.location = location;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
        this.transactionType = transactionType;
    }

    @Version
    private Long version;
    
    @EmbeddedId
    private StockLevelId id;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id", foreignKey = @jakarta.persistence.ForeignKey(name = "product_id"))
    private Product product;

    @ManyToOne
    @MapsId("location_id")
    @JoinColumn(name = "location_id", foreignKey = @jakarta.persistence.ForeignKey(name = "location_id"))
    private Location location;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "LastUpdated")
    private LocalDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "TransactionType")
    private TransactionType transactionType;

}
