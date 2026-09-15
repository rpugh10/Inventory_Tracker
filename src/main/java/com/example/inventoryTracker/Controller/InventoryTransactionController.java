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

import com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionOutDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionResponseDTO;
import com.example.inventoryTracker.Service.InventoryTransactionService;

@RestController
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService) {
        this.inventoryTransactionService = inventoryTransactionService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/inventory-transactions/{id}")
    public ResponseEntity<InventoryTransactionOutDTO> getInventoryTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryTransactionService.findInventoryTransactionById(id));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/inventory-transactions")
    public ResponseEntity<List<InventoryTransactionOutDTO>> getAllInventoryTransactions() {
        return ResponseEntity.ok(inventoryTransactionService.findAllInventoryTransactions());
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @PostMapping("/inventory-transactions")
    public ResponseEntity<InventoryTransactionOutDTO> createInventoryTransaction(
            @RequestBody InventoryTransactionRequestDTO inventoryTransactionDTO) {
        return ResponseEntity.ok(inventoryTransactionService.saveInventoryTransaction(inventoryTransactionDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/inventory-transactions/{id}")
    public ResponseEntity<InventoryTransactionOutDTO> updateInventoryTransaction(@PathVariable Long id,
            @RequestBody InventoryTransactionRequestDTO inventoryTransactionDTO) {
        return ResponseEntity.ok(inventoryTransactionService.updateInventoryTransaction(id, inventoryTransactionDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/inventory-transactions/{id}")
    public ResponseEntity<Void> deleteInventoryTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteInventoryTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
