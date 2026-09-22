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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockLevel {

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
