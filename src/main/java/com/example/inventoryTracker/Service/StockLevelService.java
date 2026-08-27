package com.example.inventoryTracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.StockLevelDTO;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.StockLevel;
import com.example.inventoryTracker.Entities.StockLevelId;
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

    public StockLevelDTO findStockLevelById(Long productId, Long locationId) {
        return stockLevelRepository.findById(new StockLevelId(productId, locationId)).map(stockLevelMapper::toStockLevelDTO)
                .orElseThrow(() -> new RuntimeException("Stock level not found"));
    }

    public List<StockLevelDTO> findAllStockLevels() {
        return stockLevelRepository.findAll().stream().map(stockLevelMapper::toStockLevelDTO).toList();
    }

    public StockLevelDTO saveStockLevel(StockLevelDTO stockLevelDTO) {
        StockLevel stockLevel = stockLevelMapper.toStockLevel(stockLevelDTO);
        setRelationshipsAndId(stockLevel, stockLevelDTO);
        return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(stockLevel));
    }

    public StockLevelDTO updateStockLevel(Long productId, Long locationId, StockLevelDTO stockLevelDTO) {
        StockLevel stockLevel = stockLevelRepository.findById(new StockLevelId(productId, locationId))
                .orElseThrow(() -> new RuntimeException("Stock level not found"));
        stockLevel.setQuantity(stockLevelDTO.getQuantity());
        stockLevel.setTransactionType(stockLevelDTO.getTransactionType());
        return stockLevelMapper.toStockLevelDTO(stockLevelRepository.save(stockLevel));
    }

    public void deleteStockLevel(Long productId, Long locationId) {
        StockLevelId id = new StockLevelId(productId, locationId);
        if (!stockLevelRepository.existsById(id)) {
            throw new RuntimeException("Stock level not found");
        }
        stockLevelRepository.deleteById(id);
    }

    private void setRelationshipsAndId(StockLevel stockLevel, StockLevelDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + dto.getLocationId()));
        stockLevel.setId(new StockLevelId(dto.getProductId(), dto.getLocationId()));
        stockLevel.setProduct(product);
        stockLevel.setLocation(location);
    }
}
