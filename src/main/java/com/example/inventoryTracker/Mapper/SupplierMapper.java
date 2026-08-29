package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.RequestDTOS.SupplierRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.SupplierResponseDTO;
import com.example.inventoryTracker.Entities.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    SupplierResponseDTO toSupplierDTO(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    Supplier toSupplier(SupplierRequestDTO supplierDTO);
}
