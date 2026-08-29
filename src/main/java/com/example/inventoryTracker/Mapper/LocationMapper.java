package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.RequestDTOS.LocationRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.LocationResponseDTO;
import com.example.inventoryTracker.Entities.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationResponseDTO toLocationDTO(Location location);

    @Mapping(target = "id", ignore = true)
    Location toLocation(LocationRequestDTO locationDTO);
}
