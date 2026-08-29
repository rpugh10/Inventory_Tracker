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

import com.example.inventoryTracker.DTO.RequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionResponseDTO;
import com.example.inventoryTracker.Service.InventoryTransactionService;

@RestController
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService) {
        this.inventoryTransactionService = inventoryTransactionService;
    }

    @GetMapping("/inventory-transactions/{id}")
    public ResponseEntity<InventoryTransactionResponseDTO> getInventoryTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryTransactionService.findInventoryTransactionById(id));
    }

    @GetMapping("/inventory-transactions")
    public ResponseEntity<List<InventoryTransactionResponseDTO>> getAllInventoryTransactions() {
        return ResponseEntity.ok(inventoryTransactionService.findAllInventoryTransactions());
    }

    @PostMapping("/inventory-transactions")
    public ResponseEntity<InventoryTransactionResponseDTO> createInventoryTransaction(
            @RequestBody InventoryTransactionRequestDTO inventoryTransactionDTO) {
        return ResponseEntity.ok(inventoryTransactionService.saveInventoryTransaction(inventoryTransactionDTO));
    }

    @PutMapping("/inventory-transactions/{id}")
    public ResponseEntity<InventoryTransactionResponseDTO> updateInventoryTransaction(@PathVariable Long id,
            @RequestBody InventoryTransactionRequestDTO inventoryTransactionDTO) {
        return ResponseEntity.ok(inventoryTransactionService.updateInventoryTransaction(id, inventoryTransactionDTO));
    }

    @DeleteMapping("/inventory-transactions/{id}")
    public ResponseEntity<Void> deleteInventoryTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteInventoryTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
