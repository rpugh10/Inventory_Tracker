package com.example.inventoryTracker.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionOutDTO;
import com.example.inventoryTracker.Service.InventoryTransactionService;

import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearerAuth")
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
    public ResponseEntity<List<InventoryTransactionOutDTO>> getAllInventoryTransactions(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(inventoryTransactionService.findAllInventoryTransactions(productId, locationId, from, to));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @PostMapping("/inventory-transactions")
    public ResponseEntity<InventoryTransactionOutDTO> createInventoryTransaction(
            @Valid @RequestBody InventoryTransactionRequestDTO inventoryTransactionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryTransactionService.saveInventoryTransaction(inventoryTransactionDTO));
    }
}
