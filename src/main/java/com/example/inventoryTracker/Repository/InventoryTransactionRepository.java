package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventoryTracker.Entities.InventoryTransaction;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
}
