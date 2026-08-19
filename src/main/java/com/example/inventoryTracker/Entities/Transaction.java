package com.example.inventoryTracker.Entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", foreignKey = @jakarta.persistence.ForeignKey(name = "userID"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplierID", foreignKey = @jakarta.persistence.ForeignKey(name = "supplierID"))
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "productID", foreignKey = @jakarta.persistence.ForeignKey(name = "productID"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "locationID", foreignKey = @jakarta.persistence.ForeignKey(name = "locationID"))
    private Location location;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "TransactionDate")
    private Timestamp transactionDate;

    @Column(name = "Note")
    private String note;

    @Column(name = "TransactionType")
    private String transactionType;
}
