package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.inventoryTracker.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
