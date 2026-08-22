package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventoryTracker.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
