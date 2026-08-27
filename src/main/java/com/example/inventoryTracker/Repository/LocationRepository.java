package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventoryTracker.Entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
