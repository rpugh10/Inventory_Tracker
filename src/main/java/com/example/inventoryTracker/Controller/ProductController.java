package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.RequestDTOS.ProductRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.ProductResponseDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Service.ProductService;
import com.example.inventoryTracker.Service.StockLevelService;

@RestController
public class ProductController {
    private final ProductService productService;
    private final StockLevelService stockLevelService;

    public ProductController(ProductService productService, StockLevelService stockLevelService) {
        this.productService = productService;
        this.stockLevelService = stockLevelService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/products/{id}/stock")
    public ResponseEntity<List<StockLevelResponseDTO>> getProductStock(@PathVariable Long id) {
        productService.findProductById(id);
        return ResponseEntity.ok(stockLevelService.findStockLevelsByProduct(id));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
