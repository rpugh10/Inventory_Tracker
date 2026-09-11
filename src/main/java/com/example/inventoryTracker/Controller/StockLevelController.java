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

import com.example.inventoryTracker.DTO.RequestDTOS.StockLevelRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Service.StockLevelService;

@RestController
public class StockLevelController {

    private final StockLevelService stockLevelService;

    public StockLevelController(StockLevelService stockLevelService) {
        this.stockLevelService = stockLevelService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/stock-levels/{productId}/{locationId}")
    public ResponseEntity<StockLevelResponseDTO> getStockLevel(@PathVariable Long productId, @PathVariable Long locationId) {
        return ResponseEntity.ok(stockLevelService.findStockLevelById(productId, locationId));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/stock-levels")
    public ResponseEntity<List<StockLevelResponseDTO>> getAllStockLevels() {
        return ResponseEntity.ok(stockLevelService.findAllStockLevels());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/stock-levels")
    public ResponseEntity<StockLevelResponseDTO> createStockLevel(@RequestBody StockLevelRequestDTO stockLevelDTO) {
        return ResponseEntity.ok(stockLevelService.saveStockLevel(stockLevelDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/stock-levels/{productId}/{locationId}")
    public ResponseEntity<StockLevelResponseDTO> updateStockLevel(@PathVariable Long productId, @PathVariable Long locationId,
            @RequestBody StockLevelRequestDTO stockLevelDTO) {
        return ResponseEntity.ok(stockLevelService.updateStockLevel(productId, locationId, stockLevelDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/stock-levels/{productId}/{locationId}")
    public ResponseEntity<Void> deleteStockLevel(@PathVariable Long productId, @PathVariable Long locationId) {
        stockLevelService.deleteStockLevel(productId, locationId);
        return ResponseEntity.noContent().build();
    }
}
