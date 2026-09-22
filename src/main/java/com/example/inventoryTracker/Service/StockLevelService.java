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
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InsufficientStock;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidQuantity;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidTransactionType;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.LocationNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.ProductNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.StockLevelIdNotFound;
import com.example.inventoryTracker.Mapper.StockLevelMapper;
import com.example.inventoryTracker.Repository.LocationRepository;
import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Repository.StockLevelRepository;
import com.example.inventoryTracker.Repository.SupplierRepository;

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
                .orElseThrow(() -> new StockLevelIdNotFound("Stock level not found for productId: " + productId + " at locationId: " + locationId));
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
                throw new InvalidTransactionType("Invalid transaction type for non-existing stock level for productId: " + productId + " at locationId: " + locationId);
            }

            StockLevel newStockLevel = new StockLevel();
            newStockLevel.setId(new StockLevelId(productId, locationId));
            newStockLevel.setQuantity(quantity);
            newStockLevel.setTransactionType(transactionType);
            StockLevelRequestDTO requestDTO = new StockLevelRequestDTO();
            requestDTO.setProductId(productId);
            requestDTO.setLocationId(locationId);
            requestDTO.setQuantity(quantity);
            requestDTO.setTransactionType(transactionType);
            setRelationshipsAndId(newStockLevel, requestDTO);
            newStockLevel.setLastUpdated(java.time.LocalDateTime.now());
            return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(newStockLevel));
        }

        StockLevel stockLevel = existingStockLevel.get();
        if(quantity == null) {
            throw new InvalidQuantity("Quantity cannot be null for productId: " + productId + " at locationId: " + locationId);
        }

        if(transactionType == TransactionType.STOCK_IN) {
            if(quantity < 0) {
                throw new InvalidQuantity("Resulting stock level cannot be negative for productId: " + productId + " at locationId: " + locationId);
            }
            stockLevel.setQuantity(stockLevel.getQuantity() + quantity);
        } else if(transactionType == TransactionType.STOCK_OUT) {
            if(stockLevel.getQuantity() == null || stockLevel.getQuantity() < quantity || stockLevel.getTransactionType() == null) {
                throw new InsufficientStock("Insufficient stock for productId: " + productId + " at locationId: " + locationId);
            }
            stockLevel.setQuantity(stockLevel.getQuantity() - quantity);
        }else if(transactionType == TransactionType.STOCK_ADJUSTMENT) {
            if(quantity < 0) {
                throw new InvalidQuantity("Resulting stock level cannot be negative for productId: " + productId + " at locationId: " + locationId);
            }
            stockLevel.setQuantity(quantity);
        }
        
        stockLevel.setTransactionType(transactionType);
        stockLevel.setLastUpdated(java.time.LocalDateTime.now());
        return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(stockLevel));
    }

    public void deleteStockLevel(Long productId, Long locationId) {
        StockLevelId id = new StockLevelId(productId, locationId);
        if (!stockLevelRepository.existsById(id)) {
            throw new StockLevelIdNotFound("Stock level not found");
        }
        stockLevelRepository.deleteById(id);
    }

    private void setRelationshipsAndId(StockLevel stockLevel, StockLevelRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFound("Product not found with id: " + dto.getProductId()));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new LocationNotFound("Location not found with id: " + dto.getLocationId()));
        stockLevel.setId(new StockLevelId(dto.getProductId(), dto.getLocationId()));
        stockLevel.setProduct(product);
        stockLevel.setLocation(location);
    }
}
