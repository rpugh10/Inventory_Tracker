package com.example.inventoryTracker.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventoryTracker.DTO.AppUserRequestDTO.AppUserDTO;
import com.example.inventoryTracker.Entities.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    
    AppUserDTO toAppUserDTO(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    AppUser toAppUser(AppUserDTO appUserDTO);
}
