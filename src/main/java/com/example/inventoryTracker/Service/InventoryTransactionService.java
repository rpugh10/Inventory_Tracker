package com.example.inventoryTracker.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionOutDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionResponseDTO;
import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Entities.InventoryTransaction;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.Supplier;
import com.example.inventoryTracker.Entities.Enums.TransactionType;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.InventoryTransactionIdNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.LocationNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.ProductNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.SupplierNotFound;
import com.example.inventoryTracker.ExceptionHandler.Exceptions.UserNotFoundException;
import com.example.inventoryTracker.Mapper.InventoryTransactionMapper;
import com.example.inventoryTracker.Repository.AppUserRepository;
import com.example.inventoryTracker.Repository.InventoryTransactionRepository;
import com.example.inventoryTracker.Repository.LocationRepository;
import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Repository.StockLevelRepository;
import com.example.inventoryTracker.Repository.SupplierRepository;



@Service
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final SupplierRepository supplierRepository;
    private final StockLevelService stockLevelService;

    public InventoryTransactionService(InventoryTransactionRepository inventoryTransactionRepository,
            InventoryTransactionMapper inventoryTransactionMapper, AppUserRepository appUserRepository,
            ProductRepository productRepository, LocationRepository locationRepository, SupplierRepository supplierRepository, StockLevelService stockLevelService) {
       
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.inventoryTransactionMapper = inventoryTransactionMapper;
        this.appUserRepository = appUserRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
        this.stockLevelService = stockLevelService;
    }



    public InventoryTransactionOutDTO findInventoryTransactionById(Long id) {
        return inventoryTransactionRepository.findById(id).map(inventoryTransactionMapper::toInventoryTransactionResponseDTO)
                .orElseThrow(() -> new InventoryTransactionIdNotFound("Inventory transaction not found with id: " + id));
    }


    public List<InventoryTransactionOutDTO> findAllInventoryTransactions() {
        return inventoryTransactionRepository.findAll().stream().map(inventoryTransactionMapper::toInventoryTransactionResponseDTO).toList();
    }

    @Transactional 
    public InventoryTransactionOutDTO saveInventoryTransaction(InventoryTransactionRequestDTO inventoryTransactionDTO) {
        InventoryTransaction transaction = inventoryTransactionMapper.toInventoryTransaction(inventoryTransactionDTO);
        setRelationships(transaction, inventoryTransactionDTO);
        transaction.setTransactionDate(LocalDateTime.now());
        stockLevelService.updateStockLevel(transaction.getProduct().getId(), transaction.getLocation().getId(), transaction.getQuantity(), transaction.getTransactionType());
        return inventoryTransactionMapper.toInventoryTransactionResponseDTO(inventoryTransactionRepository.save(transaction));
    }

    public InventoryTransactionOutDTO updateInventoryTransaction(Long id, InventoryTransactionRequestDTO inventoryTransactionDTO) {
        InventoryTransaction transaction = inventoryTransactionRepository.findById(id)
                .orElseThrow(() -> new InventoryTransactionIdNotFound("Inventory transaction not found with id: " + id));
        transaction.setQuantity(inventoryTransactionDTO.getQuantity());
        transaction.setTransactionType(inventoryTransactionDTO.getTransactionTypeEnum());
        setRelationships(transaction, inventoryTransactionDTO);
        stockLevelService.updateStockLevel(transaction.getProduct().getId(), transaction.getLocation().getId(), transaction.getQuantity(), transaction.getTransactionType());
        transaction.setTransactionDate(LocalDateTime.now());
        return inventoryTransactionMapper.toInventoryTransactionResponseDTO(inventoryTransactionRepository.save(transaction));
    }

    public void deleteInventoryTransaction(Long id) {
        if (!inventoryTransactionRepository.existsById(id)) {
            throw new InventoryTransactionIdNotFound("Inventory transaction not found with id: " + id);
        }
        stockLevelService.updateStockLevel(inventoryTransactionRepository.findById(id).get().getProduct().getId(), inventoryTransactionRepository.findById(id).get().getLocation().getId(), -inventoryTransactionRepository.findById(id).get().getQuantity(), inventoryTransactionRepository.findById(id).get().getTransactionType());
        inventoryTransactionRepository.deleteById(id);
    }

    private void setRelationships(InventoryTransaction transaction, InventoryTransactionRequestDTO dto) {
        AppUser user = appUserRepository.findById(dto.getAppUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getAppUserId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFound("Product not found with id: " + dto.getProductId()));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new LocationNotFound("Location not found with id: " + dto.getLocationId()));

        if(dto.getTransactionTypeEnum() == TransactionType.STOCK_IN){
            if(dto.getSupplierId() == null){
                throw new SupplierNotFound("Supplier ID is required for STOCK_IN transactions");
                }

            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new SupplierNotFound("Supplier not found with id: " + dto.getSupplierId()));
            transaction.setSupplier(supplier);
        }else{
            transaction.setSupplier(null);
        }
        
        transaction.setUser(user);
        transaction.setProduct(product);
        transaction.setLocation(location);
      
    }
}
