package com.example.inventoryTracker.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;

public interface StockLevelRepository extends JpaRepository<StockLevel, StockLevelId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT stockLevel FROM StockLevel stockLevel WHERE stockLevel.id = :id")
    Optional<StockLevel> findByIdForUpdate(@Param("id") StockLevelId id);

    List<StockLevel> findByLocation_Id(Long locationId);

    List<StockLevel> findByProduct_Id(Long productId);
}
