package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;

import com.example.inventoryTracker.DTO.ProductDTO;
import com.example.inventoryTracker.Entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(Product product);
}
