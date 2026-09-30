package com.example.inventoryTracker.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventoryTracker.DTO.RequestDTOS.StockLevelRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.StockLevelIdNotFound;
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

    @Test 
    void testFindStockLevelByIdNotFound() {
        StockLevelId stockLevelId = new StockLevelId(1L, 1L);

        // Mock the behavior of the ProductRepository to return an empty Optional
        when(stockLevelRepository.findById(stockLevelId)).thenReturn(java.util.Optional.empty());

        // Call the method under test and assert that it throws an exception
        org.junit.jupiter.api.Assertions.assertThrows(
            StockLevelIdNotFound.class,
            () -> stockLevelService.findStockLevelById(1L, 1L)
        );
    }

    @Test 
    void testFindAllStockLevels() {
        StockLevel stockLevel1 = new StockLevel();
        stockLevel1.setId(new StockLevelId(1L, 1L));
        stockLevel1.setQuantity(100);
        stockLevel1.setLastUpdated(java.time.LocalDateTime.now());
        stockLevel1.setTransactionType(com.example.inventoryTracker.Entities.Enums.TransactionType.STOCK_IN);

        StockLevel stockLevel2 = new StockLevel();
        stockLevel2.setId(new StockLevelId(2L, 2L));
        stockLevel2.setQuantity(200);
        stockLevel2.setLastUpdated(java.time.LocalDateTime.now());
        stockLevel2.setTransactionType(com.example.inventoryTracker.Entities.Enums.TransactionType.STOCK_OUT);

        java.util.List<StockLevel> expectedStockLevels = java.util.Arrays.asList(stockLevel1, stockLevel2);

        StockLevelResponseDTO responseDTO1 = new StockLevelResponseDTO();
        responseDTO1.setQuantity(100);

        StockLevelResponseDTO responseDTO2 = new StockLevelResponseDTO();
        responseDTO2.setQuantity(200);

        java.util.List<StockLevelResponseDTO> expectedResponse = java.util.Arrays.asList(responseDTO1, responseDTO2);

        // Mock the behavior of the ProductRepository
        when(stockLevelRepository.findAll()).thenReturn(expectedStockLevels);
        when(stockLevelMapper.toStockLevelDTO(stockLevel1)).thenReturn(responseDTO1);
        when(stockLevelMapper.toStockLevelDTO(stockLevel2)).thenReturn(responseDTO2);

        // Call the method under test
      java.util.List<StockLevelResponseDTO> actualResponse = stockLevelService.findAllStockLevels();
        
      // Verify the result
      assertEquals(expectedResponse.size(), actualResponse.size());
      assertEquals(expectedResponse.get(0).getQuantity(), actualResponse.get(0).getQuantity());
      assertEquals(expectedResponse.get(1).getQuantity(), actualResponse.get(1).getQuantity());
    }

    @Test 
    void testSaveStockLevel() {
        StockLevelRequestDTO requestDTO = new StockLevelRequestDTO();
        requestDTO.setProductId(1L);
        requestDTO.setLocationId(1L);
        requestDTO.setQuantity(100);
        requestDTO.setTransactionType(com.example.inventoryTracker.Entities.Enums.TransactionType.STOCK_IN);

        StockLevel stockLevel = new StockLevel();
        stockLevel.setId(new StockLevelId(1L, 1L));
        stockLevel.setQuantity(100);
        stockLevel.setLastUpdated(java.time.LocalDateTime.now());
        stockLevel.setTransactionType(com.example.inventoryTracker.Entities.Enums.TransactionType.STOCK_IN);

        StockLevelResponseDTO expectedResponse = new StockLevelResponseDTO();
        expectedResponse.setQuantity(100);

        // Mock the behavior of the ProductRepository
        when(stockLevelMapper.toStockLevel(requestDTO)).thenReturn(stockLevel);
        when(stockLevelRepository.save(stockLevel)).thenReturn(stockLevel);
        when(stockLevelMapper.toStockLevelDTO(stockLevel)).thenReturn(expectedResponse);

        // Call the method under test
      StockLevelResponseDTO actualResponse = stockLevelService.saveStockLevel(requestDTO);
        
      // Verify the result
      assertEquals(expectedResponse.getQuantity(), actualResponse.getQuantity());
    }
}
