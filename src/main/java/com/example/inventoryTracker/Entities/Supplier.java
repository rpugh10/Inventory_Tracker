package com.example.inventoryTracker.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SupplierName")
    private String supplierName;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    public Supplier() {
    }

    public Supplier(Long id, String supplierName, String email, String phoneNumber) {
        this.id = id;
        this.supplierName = supplierName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
