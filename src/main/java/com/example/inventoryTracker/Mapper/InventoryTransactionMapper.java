package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.example.inventoryTracker.DTO.RequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionResponseDTO;
import com.example.inventoryTracker.Entities.InventoryTransaction;



@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {

    @Mapping(target = "appUserId", source = "user.id")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "transactionTypeEnum", source = "transactionType")
    InventoryTransactionResponseDTO toInventoryTransactionResponseDTO(InventoryTransaction inventoryTransaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "transactionType", source = "transactionTypeEnum")
    InventoryTransaction toInventoryTransaction(InventoryTransactionRequestDTO inventoryTransactionDTO);
}
