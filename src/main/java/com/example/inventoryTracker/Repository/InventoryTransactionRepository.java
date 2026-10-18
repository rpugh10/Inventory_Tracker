package com.example.inventoryTracker.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.inventoryTracker.Entities.InventoryTransaction;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    @Query("""
            SELECT t FROM InventoryTransaction t
            WHERE (:productId IS NULL OR t.product.id = :productId)
              AND (:locationId IS NULL OR t.location.id = :locationId)
              AND (:from IS NULL OR t.transactionDate >= :from)
              AND (:to IS NULL OR t.transactionDate <= :to)
            ORDER BY t.transactionDate DESC
            """)
    List<InventoryTransaction> findByFilters(
            @Param("productId") Long productId,
            @Param("locationId") Long locationId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}
