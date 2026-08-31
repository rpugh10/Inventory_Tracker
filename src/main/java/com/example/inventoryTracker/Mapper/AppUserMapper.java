package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.RequestDTOS.UserRequestDTOS.AppUserRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.AppUserResponseDTO;
import com.example.inventoryTracker.Entities.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    
    AppUserResponseDTO toAppUserDTO(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    AppUser toAppUser(AppUserRequestDTO appUserDTO);
}
