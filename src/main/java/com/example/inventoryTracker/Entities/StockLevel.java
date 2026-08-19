package com.example.inventoryTracker.Entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CompositeType;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @JoinColumn(name = "productID", foreignKey = @jakarta.persistence.ForeignKey(name = "productID"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "locationID", foreignKey = @jakarta.persistence.ForeignKey(name = "locationID"))
    private Location location;

    @Column(name = "Quantity")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "TransactionType")
    private TransactionType transactionType;

}
