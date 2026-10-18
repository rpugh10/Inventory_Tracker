package com.example.inventoryTracker.Tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Entities.Enums.TransactionType;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InsufficientStock;
import com.example.inventoryTracker.Mapper.InventoryTransactionMapper;
import com.example.inventoryTracker.Repository.AppUserRepository;
import com.example.inventoryTracker.Repository.InventoryTransactionRepository;
import com.example.inventoryTracker.Repository.LocationRepository;
import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Repository.SupplierRepository;
import com.example.inventoryTracker.Service.InventoryTransactionService;
import com.example.inventoryTracker.Service.StockLevelService;

@ExtendWith(MockitoExtension.class)
class InventoryTransactionServiceTest {
    @Mock private InventoryTransactionRepository transactionRepository;
    @Mock private InventoryTransactionMapper transactionMapper;
    @Mock private AppUserRepository userRepository;
    @Mock private ProductRepository productRepository;
    @Mock private LocationRepository locationRepository;
    @Mock private SupplierRepository supplierRepository;
    @Mock private StockLevelService stockLevelService;
    @InjectMocks private InventoryTransactionService transactionService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doesNotWriteLedgerWhenStockMovementFails() {
        Product product = new Product();
        product.setId(1L);
        Location location = new Location();
        location.setId(2L);
        InventoryTransactionRequestDTO request = new InventoryTransactionRequestDTO();
        request.setProductId(1L);
        request.setLocationId(2L);
        request.setQuantity(8);
        request.setTransactionType(TransactionType.STOCK_OUT);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(location));
        org.mockito.Mockito.doThrow(new InsufficientStock("Insufficient stock"))
                .when(stockLevelService).applyMovement(product, location, 8, TransactionType.STOCK_OUT);

        assertThrows(InsufficientStock.class, () -> transactionService.saveInventoryTransaction(request));

        verify(transactionRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }
}
