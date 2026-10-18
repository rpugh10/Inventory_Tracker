package com.example.inventoryTracker.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionOutDTO;
import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Entities.Enums.TransactionType;
import com.example.inventoryTracker.Entities.InventoryTransaction;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.Supplier;
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
import com.example.inventoryTracker.Repository.SupplierRepository;

@Service
public class InventoryTransactionService {
    private final InventoryTransactionRepository transactionRepository;
    private final InventoryTransactionMapper transactionMapper;
    private final AppUserRepository userRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final SupplierRepository supplierRepository;
    private final StockLevelService stockLevelService;

    public InventoryTransactionService(InventoryTransactionRepository transactionRepository,
            InventoryTransactionMapper transactionMapper, AppUserRepository userRepository,
            ProductRepository productRepository, LocationRepository locationRepository,
            SupplierRepository supplierRepository, StockLevelService stockLevelService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
        this.stockLevelService = stockLevelService;
    }

    public InventoryTransactionOutDTO findInventoryTransactionById(Long id) {
        return transactionRepository.findById(id).map(transactionMapper::toInventoryTransactionResponseDTO)
                .orElseThrow(() -> new InventoryTransactionIdNotFound("Inventory transaction not found with id: " + id));
    }

    public List<InventoryTransactionOutDTO> findAllInventoryTransactions(Long productId, Long locationId,
            LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findByFilters(productId, locationId, from, to).stream()
                .map(transactionMapper::toInventoryTransactionResponseDTO).toList();
    }

    /** Records one immutable stock movement. The snapshot and ledger entry commit or roll back together. */
    @Transactional
    public InventoryTransactionOutDTO saveInventoryTransaction(InventoryTransactionRequestDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFound("Product not found with id: " + request.getProductId()));
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new LocationNotFound("Location not found with id: " + request.getLocationId()));
        Supplier supplier = resolveSupplier(request);

        stockLevelService.applyMovement(product, location, request.getQuantity(), request.getTransactionType());
        InventoryTransaction transaction = transactionMapper.toInventoryTransaction(request);
        transaction.setProduct(product);
        transaction.setLocation(location);
        transaction.setSupplier(supplier);
        transaction.setUser(currentUser());
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionMapper.toInventoryTransactionResponseDTO(transactionRepository.save(transaction));
    }

    private Supplier resolveSupplier(InventoryTransactionRequestDTO request) {
        if (request.getTransactionType() != TransactionType.STOCK_IN) {
            return null;
        }
        if (request.getSupplierId() == null) {
            throw new SupplierNotFound("supplierId is required for STOCK_IN transactions");
        }
        return supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new SupplierNotFound("Supplier not found with id: " + request.getSupplierId()));
    }

    private AppUser currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException("An authenticated user is required to record a transaction");
        }
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Authenticated user no longer exists"));
    }
}
