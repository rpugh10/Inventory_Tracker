package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.RequestDTOS.TransactionRequestDTOS.InventoryTransactionRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.InventoryTransactionOutDTO;
import com.example.inventoryTracker.Entities.InventoryTransaction;



@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "transactionDate", source = "transactionDate")
    InventoryTransactionOutDTO toInventoryTransactionResponseDTO(InventoryTransaction inventoryTransaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "location", ignore = true)
    InventoryTransaction toInventoryTransaction(InventoryTransactionRequestDTO inventoryTransactionDTO);
}
