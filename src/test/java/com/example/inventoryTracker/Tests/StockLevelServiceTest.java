package com.example.inventoryTracker.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventoryTracker.Entities.Enums.TransactionType;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InsufficientStock;
import com.example.inventoryTracker.Mapper.StockLevelMapper;
import com.example.inventoryTracker.Repository.StockLevelRepository;
import com.example.inventoryTracker.Service.StockLevelService;

@ExtendWith(MockitoExtension.class)
class StockLevelServiceTest {
    @Mock private StockLevelRepository stockLevelRepository;
    @Mock private StockLevelMapper stockLevelMapper;
    @InjectMocks private StockLevelService stockLevelService;

    @Test
    void stockInCreatesFirstStockLevel() {
        Product product = product(1L);
        Location location = location(2L);
        StockLevelId id = new StockLevelId(1L, 2L);
        when(stockLevelRepository.findByIdForUpdate(id)).thenReturn(Optional.empty());
        when(stockLevelRepository.save(any(StockLevel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StockLevel result = stockLevelService.applyMovement(product, location, 12, TransactionType.STOCK_IN);

        assertEquals(12, result.getQuantity());
        assertEquals(product, result.getProduct());
        assertEquals(location, result.getLocation());
        assertNotNull(result.getLastUpdated());
        verify(stockLevelRepository).save(result);
    }

    @Test
    void stockOutRejectsQuantityGreaterThanAvailable() {
        Product product = product(1L);
        Location location = location(2L);
        StockLevelId id = new StockLevelId(1L, 2L);
        StockLevel existing = new StockLevel();
        existing.setId(id);
        existing.setQuantity(4);
        when(stockLevelRepository.findByIdForUpdate(id)).thenReturn(Optional.of(existing));

        assertThrows(InsufficientStock.class,
                () -> stockLevelService.applyMovement(product, location, 5, TransactionType.STOCK_OUT));

        verify(stockLevelRepository, never()).save(any());
    }

    @Test
    void stockOutReducesExistingStock() {
        Product product = product(1L);
        Location location = location(2L);
        StockLevelId id = new StockLevelId(1L, 2L);
        StockLevel existing = new StockLevel();
        existing.setId(id);
        existing.setQuantity(10);
        when(stockLevelRepository.findByIdForUpdate(id)).thenReturn(Optional.of(existing));
        when(stockLevelRepository.save(existing)).thenReturn(existing);

        StockLevel result = stockLevelService.applyMovement(product, location, 3, TransactionType.STOCK_OUT);

        assertEquals(7, result.getQuantity());
        assertNotNull(result.getLastUpdated());
    }

    private Product product(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    private Location location(Long id) {
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
