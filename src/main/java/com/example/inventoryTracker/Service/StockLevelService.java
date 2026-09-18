package com.example.inventoryTracker.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.RequestDTOS.StockLevelRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;
import com.example.inventoryTracker.Entities.Enums.TransactionType;
import com.example.inventoryTracker.Mapper.StockLevelMapper;
import com.example.inventoryTracker.Repository.LocationRepository;
import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Repository.StockLevelRepository;

@Service
public class StockLevelService {

    private final StockLevelRepository stockLevelRepository;
    private final StockLevelMapper stockLevelMapper;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    public StockLevelService(StockLevelRepository stockLevelRepository, StockLevelMapper stockLevelMapper,
            ProductRepository productRepository, LocationRepository locationRepository) {
        this.stockLevelRepository = stockLevelRepository;
        this.stockLevelMapper = stockLevelMapper;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    public StockLevelResponseDTO findStockLevelById(Long productId, Long locationId) {
        return stockLevelRepository.findById(new StockLevelId(productId, locationId)).map(stockLevelMapper::toStockLevelDTO)
                .orElseThrow(() -> new RuntimeException("Stock level not found"));
    }

    public List<StockLevelResponseDTO> findAllStockLevels() {
        return stockLevelRepository.findAll().stream().map(stockLevelMapper::toStockLevelDTO).toList();
    }

    public StockLevelResponseDTO saveStockLevel(StockLevelRequestDTO stockLevelDTO) {
        StockLevel stockLevel = stockLevelMapper.toStockLevel(stockLevelDTO);
        setRelationshipsAndId(stockLevel, stockLevelDTO);
        return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(stockLevel));
    }

    public StockLevelResponseDTO updateStockLevel(Long productId, Long locationId, Integer quantity, TransactionType transactionType) {
        Optional<StockLevel> existingStockLevel = stockLevelRepository.findById(new StockLevelId(productId, locationId));
        
        if(existingStockLevel.isEmpty()){
            if(transactionType == TransactionType.STOCK_OUT || transactionType == TransactionType.STOCK_ADJUSTMENT) {
                throw new RuntimeException("Stock level not found for productId: " + productId + " at locationId: " + locationId);
            }

            StockLevel newStockLevel = new StockLevel();
            newStockLevel.setId(new StockLevelId(productId, locationId));
            newStockLevel.setQuantity(quantity);
            newStockLevel.setTransactionType(transactionType);
            StockLevelRequestDTO requestDTO = new StockLevelRequestDTO();
            requestDTO.setProductId(productId);
            requestDTO.setLocationId(locationId);
            setRelationshipsAndId(newStockLevel, requestDTO);
            return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(newStockLevel));
        }

        StockLevel stockLevel = existingStockLevel.get();

        if(transactionType == TransactionType.STOCK_IN) {
            if(stockLevel.getQuantity() + quantity < 0 || stockLevel.getQuantity() == null) {
                throw new RuntimeException("Resulting stock level cannot be negative for productId: " + productId + " at locationId: " + locationId);
            }
            stockLevel.setQuantity(stockLevel.getQuantity() + quantity);
        } else if(transactionType == TransactionType.STOCK_OUT) {
            if(stockLevel.getQuantity() < quantity || stockLevel.getQuantity() == null || stockLevel.getTransactionType() == null) {
                throw new RuntimeException("Insufficient stock for productId: " + productId + " at locationId: " + locationId);
            }
            stockLevel.setQuantity(stockLevel.getQuantity() - quantity);
        }else if(transactionType == TransactionType.STOCK_ADJUSTMENT) {
            if(quantity == null) {
                throw new RuntimeException("Quantity cannot be null for stock adjustment for productId: " + productId + " at locationId: " + locationId);
            }
            stockLevel.setQuantity(quantity);
        }
        
        stockLevel.setTransactionType(transactionType);
        return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(stockLevel));
    }

    public void deleteStockLevel(Long productId, Long locationId) {
        StockLevelId id = new StockLevelId(productId, locationId);
        if (!stockLevelRepository.existsById(id)) {
            throw new RuntimeException("Stock level not found");
        }
        stockLevelRepository.deleteById(id);
    }

    private void setRelationshipsAndId(StockLevel stockLevel, StockLevelRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + dto.getLocationId()));
        stockLevel.setId(new StockLevelId(dto.getProductId(), dto.getLocationId()));
        stockLevel.setProduct(product);
        stockLevel.setLocation(location);
    }
}
