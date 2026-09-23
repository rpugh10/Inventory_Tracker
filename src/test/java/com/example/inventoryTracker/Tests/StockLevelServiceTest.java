package com.example.inventoryTracker.Tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Service.ProductService;

@ExtendWith(MockitoExtension.class)
public class StockLevelServiceTest {

    @Mock 
    private ProductRepository productRepository; //Creates fake instance of ProductRepository to be used in the test

    @InjectMocks 
    private ProductService productService; //Creates an instance of ProductService and injects the mocked ProductRepository into it

    // Add test methods here
    @Test 
    void testFindStockLevelById() {
        
    }
}
