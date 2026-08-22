package com.example.inventoryTracker.Controller;

import org.springframework.stereotype.Controller;

import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getMethodName(@RequestParam String name) {
        return new String("hello");
    }
    
}
