package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.SupplierDTO;
import com.example.inventoryTracker.Entities.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    SupplierDTO toSupplierDTO(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    Supplier toSupplier(SupplierDTO supplierDTO);
}
