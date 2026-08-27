package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;

import com.example.inventoryTracker.DTO.LocationDTO;
import com.example.inventoryTracker.Entities.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDTO toLocationDTO(Location location);

    Location toLocation(LocationDTO locationDTO);
}
