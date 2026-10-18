package com.example.inventoryTracker.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.ResponseDTOS.StockLevelResponseDTO;
import com.example.inventoryTracker.Entities.Enums.TransactionType;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InsufficientStock;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidQuantity;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InvalidTransactionType;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.StockLevelIdNotFound;
import com.example.inventoryTracker.Mapper.StockLevelMapper;
import com.example.inventoryTracker.Repository.StockLevelRepository;

@Service
public class StockLevelService {
    private final StockLevelRepository stockLevelRepository;
    private final StockLevelMapper stockLevelMapper;

    public StockLevelService(StockLevelRepository stockLevelRepository, StockLevelMapper stockLevelMapper) {
        this.stockLevelRepository = stockLevelRepository;
        this.stockLevelMapper = stockLevelMapper;
    }

    public StockLevelResponseDTO findStockLevelById(Long productId, Long locationId) {
        return stockLevelRepository.findById(new StockLevelId(productId, locationId))
                .map(stockLevelMapper::toStockLevelDTO)
                .orElseThrow(() -> new StockLevelIdNotFound(
                        "Stock level not found for productId: " + productId + " at locationId: " + locationId));
    }

    public List<StockLevelResponseDTO> findAllStockLevels(Long locationId) {
        List<StockLevel> levels = locationId == null ? stockLevelRepository.findAll()
                : stockLevelRepository.findByLocation_Id(locationId);
        return levels.stream().map(stockLevelMapper::toStockLevelDTO).toList();
    }

    public List<StockLevelResponseDTO> findStockLevelsByProduct(Long productId) {
        return stockLevelRepository.findByProduct_Id(productId).stream()
                .map(stockLevelMapper::toStockLevelDTO).toList();
    }

    /** Applies one movement inside the transaction opened by InventoryTransactionService. */
    public StockLevel applyMovement(Product product, Location location, Integer quantity, TransactionType type) {
        validateMovement(quantity, type);
        StockLevelId id = new StockLevelId(product.getId(), location.getId());
        StockLevel stockLevel = stockLevelRepository.findByIdForUpdate(id).orElse(null);

        if (stockLevel == null) {
            if (type != TransactionType.STOCK_IN) {
                throw new InvalidTransactionType("Only STOCK_IN can create stock for productId: " + product.getId()
                        + " at locationId: " + location.getId());
            }
            stockLevel = new StockLevel();
            stockLevel.setId(id);
            stockLevel.setProduct(product);
            stockLevel.setLocation(location);
            stockLevel.setQuantity(quantity);
        } else if (type == TransactionType.STOCK_IN) {
            stockLevel.setQuantity(stockLevel.getQuantity() + quantity);
        } else if (type == TransactionType.STOCK_OUT) {
            if (stockLevel.getQuantity() < quantity) {
                throw new InsufficientStock("Insufficient stock for productId: " + product.getId()
                        + " at locationId: " + location.getId());
            }
            stockLevel.setQuantity(stockLevel.getQuantity() - quantity);
        } else { // An adjustment is an absolute count, not a delta.
            stockLevel.setQuantity(quantity);
        }

        stockLevel.setTransactionType(type);
        stockLevel.setLastUpdated(LocalDateTime.now());
        return stockLevelRepository.save(stockLevel);
    }

    private void validateMovement(Integer quantity, TransactionType type) {
        if (quantity == null || quantity <= 0) {
            throw new InvalidQuantity("quantity must be greater than zero");
        }
        if (type == null) {
            throw new InvalidTransactionType("transactionType is required");
        }
    }
}
