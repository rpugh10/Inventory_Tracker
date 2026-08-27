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

import com.example.inventoryTracker.DTO.InventoryTransactionDTO;
import com.example.inventoryTracker.Service.InventoryTransactionService;

@RestController
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService) {
        this.inventoryTransactionService = inventoryTransactionService;
    }

    @GetMapping("/inventory-transactions/{id}")
    public ResponseEntity<InventoryTransactionDTO> getInventoryTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryTransactionService.findInventoryTransactionById(id));
    }

    @GetMapping("/inventory-transactions")
    public ResponseEntity<List<InventoryTransactionDTO>> getAllInventoryTransactions() {
        return ResponseEntity.ok(inventoryTransactionService.findAllInventoryTransactions());
    }

    @PostMapping("/inventory-transactions")
    public ResponseEntity<InventoryTransactionDTO> createInventoryTransaction(
            @RequestBody InventoryTransactionDTO inventoryTransactionDTO) {
        return ResponseEntity.ok(inventoryTransactionService.saveInventoryTransaction(inventoryTransactionDTO));
    }

    @PutMapping("/inventory-transactions/{id}")
    public ResponseEntity<InventoryTransactionDTO> updateInventoryTransaction(@PathVariable Long id,
            @RequestBody InventoryTransactionDTO inventoryTransactionDTO) {
        return ResponseEntity.ok(inventoryTransactionService.updateInventoryTransaction(id, inventoryTransactionDTO));
    }

    @DeleteMapping("/inventory-transactions/{id}")
    public ResponseEntity<Void> deleteInventoryTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteInventoryTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
