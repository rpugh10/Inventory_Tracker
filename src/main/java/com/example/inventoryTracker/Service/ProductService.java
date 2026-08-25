package com.example.inventoryTracker.Service;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.ProductDTO;
import com.example.inventoryTracker.Mapper.ProductMapper;
import com.example.inventoryTracker.Repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductDTO)
                .orElseGet(() -> {
                    throw new RuntimeException("Product not found with id: " + id);
                });
    }

    
}
