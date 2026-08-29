package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.RequestDTOS.ProductRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.ProductResponseDTO;
import com.example.inventoryTracker.Entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toProductDTO(Product product);

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequestDTO productDTO);
}
