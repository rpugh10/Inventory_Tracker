package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.StockLevelDTO;
import com.example.inventoryTracker.Service.StockLevelService;

@RestController
public class StockLevelController {

    private final StockLevelService stockLevelService;

    public StockLevelController(StockLevelService stockLevelService) {
        this.stockLevelService = stockLevelService;
    }

    @GetMapping("/stock-levels/{productId}/{locationId}")
    public ResponseEntity<StockLevelDTO> getStockLevel(@PathVariable Long productId, @PathVariable Long locationId) {
        return ResponseEntity.ok(stockLevelService.findStockLevelById(productId, locationId));
    }

    @GetMapping("/stock-levels")
    public ResponseEntity<List<StockLevelDTO>> getAllStockLevels() {
        return ResponseEntity.ok(stockLevelService.findAllStockLevels());
    }

    @PostMapping("/stock-levels")
    public ResponseEntity<StockLevelDTO> createStockLevel(@RequestBody StockLevelDTO stockLevelDTO) {
        return ResponseEntity.ok(stockLevelService.saveStockLevel(stockLevelDTO));
    }

    @PutMapping("/stock-levels/{productId}/{locationId}")
    public ResponseEntity<StockLevelDTO> updateStockLevel(@PathVariable Long productId, @PathVariable Long locationId,
            @RequestBody StockLevelDTO stockLevelDTO) {
        return ResponseEntity.ok(stockLevelService.updateStockLevel(productId, locationId, stockLevelDTO));
    }

    @DeleteMapping("/stock-levels/{productId}/{locationId}")
    public ResponseEntity<Void> deleteStockLevel(@PathVariable Long productId, @PathVariable Long locationId) {
        stockLevelService.deleteStockLevel(productId, locationId);
        return ResponseEntity.noContent().build();
    }
}
