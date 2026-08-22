package com.example.inventoryTracker.Service;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.Repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
