package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Service.StockLevelService;

/** Current stock snapshot endpoints. Stock mutations happen only through inventory transactions. */
@RestController
public class StockLevelController {
    private final StockLevelService stockLevelService;

    public StockLevelController(StockLevelService stockLevelService) {
        this.stockLevelService = stockLevelService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/stock-levels")
    public ResponseEntity<List<StockLevelResponseDTO>> getStockLevels(
            @RequestParam(required = false) Long locationId) {
        return ResponseEntity.ok(stockLevelService.findAllStockLevels(locationId));
    }
}
