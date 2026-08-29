package com.example.inventoryTracker.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


import com.example.inventoryTracker.DTO.RequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionResponseDTO;
import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Entities.InventoryTransaction;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Entities.Supplier;
import com.example.inventoryTracker.Mapper.InventoryTransactionMapper;
import com.example.inventoryTracker.Repository.AppUserRepository;
import com.example.inventoryTracker.Repository.InventoryTransactionRepository;
import com.example.inventoryTracker.Repository.LocationRepository;
import com.example.inventoryTracker.Repository.ProductRepository;
import com.example.inventoryTracker.Repository.SupplierRepository;



@Service
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final SupplierRepository supplierRepository;

    public InventoryTransactionService(InventoryTransactionRepository inventoryTransactionRepository,
            InventoryTransactionMapper inventoryTransactionMapper, AppUserRepository appUserRepository,
            ProductRepository productRepository, LocationRepository locationRepository, SupplierRepository supplierRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.inventoryTransactionMapper = inventoryTransactionMapper;
        this.appUserRepository = appUserRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
    }


    public InventoryTransactionResponseDTO findInventoryTransactionById(Long id) {
        return inventoryTransactionRepository.findById(id).map(inventoryTransactionMapper::toInventoryTransactionResponseDTO)
                .orElseThrow(() -> new RuntimeException("Inventory transaction not found with id: " + id));
    }


    public List<InventoryTransactionResponseDTO> findAllInventoryTransactions() {
        return inventoryTransactionRepository.findAll().stream().map(inventoryTransactionMapper::toInventoryTransactionResponseDTO).toList();
    }

    public InventoryTransactionResponseDTO saveInventoryTransaction(InventoryTransactionRequestDTO inventoryTransactionDTO) {
        InventoryTransaction transaction = inventoryTransactionMapper.toInventoryTransaction(inventoryTransactionDTO);
        setRelationships(transaction, inventoryTransactionDTO);
        transaction.setTransactionDate(LocalDateTime.now());
        return inventoryTransactionMapper.toInventoryTransactionResponseDTO(inventoryTransactionRepository.save(transaction));
    }

    public InventoryTransactionResponseDTO updateInventoryTransaction(Long id, InventoryTransactionRequestDTO inventoryTransactionDTO) {
        InventoryTransaction transaction = inventoryTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory transaction not found with id: " + id));
        transaction.setQuantity(inventoryTransactionDTO.getQuantity());
        transaction.setNote(inventoryTransactionDTO.getNote());
        transaction.setTransactionType(inventoryTransactionDTO.getTransactionTypeEnum());
        setRelationships(transaction, inventoryTransactionDTO);
        return inventoryTransactionMapper.toInventoryTransactionResponseDTO(inventoryTransactionRepository.save(transaction));
    }

    public void deleteInventoryTransaction(Long id) {
        if (!inventoryTransactionRepository.existsById(id)) {
            throw new RuntimeException("Inventory transaction not found with id: " + id);
        }
        inventoryTransactionRepository.deleteById(id);
    }

    private void setRelationships(InventoryTransaction transaction, InventoryTransactionRequestDTO dto) {
        AppUser user = appUserRepository.findById(dto.getAppUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getAppUserId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + dto.getLocationId()));
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));
        if (supplier == null) {
            throw new RuntimeException("Supplier not found with id: " + dto.getSupplierId());
        }
        transaction.setUser(user);
        transaction.setProduct(product);
        transaction.setLocation(location);
        transaction.setSupplier(supplier);
    }
}
