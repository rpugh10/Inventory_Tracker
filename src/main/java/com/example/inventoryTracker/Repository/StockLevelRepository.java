package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;

public interface StockLevelRepository extends JpaRepository<StockLevel, StockLevelId> {
}
