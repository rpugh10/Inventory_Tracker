package com.example.inventoryTracker.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;
import com.example.inventoryTracker.Mapper.StockLevelMapper;
import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Repository.StockLevelRepository;
import com.example.inventoryTracker.Service.StockLevelService;

@ExtendWith(MockitoExtension.class)
public class StockLevelServiceTest {

    @Mock 
    private StockLevelRepository stockLevelRepository; //Creates fake instance of StockLevelRepository to be used in the test

    @Mock
    private StockLevelMapper stockLevelMapper; //Creates fake instance of StockLevelMapper to be used in the test
    
    @InjectMocks 
    private StockLevelService stockLevelService; //Creates an instance of StockLevelService and injects the mocked ProductRepository into it

    
    // Add test methods here
    @Test 
    void testFindStockLevelById() {
        StockLevelId stockLevelId = new StockLevelId(1L, 1L);
        StockLevel expectedStock = new StockLevel();
        expectedStock.setId(stockLevelId);
        expectedStock.setQuantity(100);
        expectedStock.setLastUpdated(java.time.LocalDateTime.now());
        expectedStock.setTransactionType(com.example.inventoryTracker.Entities.Enums.TransactionType.STOCK_IN);

        StockLevelResponseDTO expectedResponse = new StockLevelResponseDTO();
        expectedResponse.setQuantity(100);

        // Mock the behavior of the ProductRepository
        when(stockLevelRepository.findById(stockLevelId)).thenReturn(java.util.Optional.of(expectedStock));


        when(stockLevelMapper.toStockLevelDTO(expectedStock))
        .thenReturn(expectedResponse);

        // Call the method under test
      StockLevelResponseDTO actualResponse = stockLevelService.findStockLevelById(1L, 1L);
        
      // Verify the result
        assertEquals(expectedResponse.getQuantity(), actualResponse.getQuantity());
    }
}
