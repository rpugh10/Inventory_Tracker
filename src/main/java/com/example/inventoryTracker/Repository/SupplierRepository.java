package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventoryTracker.Entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
