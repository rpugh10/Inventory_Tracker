package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.StockLevelDTO;
import com.example.inventoryTracker.Entities.StockLevel;

@Mapper(componentModel = "spring")
public interface StockLevelMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "locationId", source = "location.id")
    StockLevelDTO toStockLevelDTO(StockLevel stockLevel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "location", ignore = true)
    StockLevel toStockLevel(StockLevelDTO stockLevelDTO);
}
