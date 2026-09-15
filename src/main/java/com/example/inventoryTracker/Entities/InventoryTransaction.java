package com.example.inventoryTracker.Entities;

import java.time.LocalDateTime;

import com.example.inventoryTracker.Entities.Enums.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class InventoryTransaction {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @jakarta.persistence.ForeignKey(name = "user_id"))
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "supplier_id", foreignKey = @jakarta.persistence.ForeignKey(name = "supplier_id"))
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @jakarta.persistence.ForeignKey(name = "product_id"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "location_id", foreignKey = @jakarta.persistence.ForeignKey(name = "location_id"))
    private Location location;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "TransactionDate")
    private LocalDateTime transactionDate;

    @Column(name = "Note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "TransactionType")
    private TransactionType transactionType;
}
