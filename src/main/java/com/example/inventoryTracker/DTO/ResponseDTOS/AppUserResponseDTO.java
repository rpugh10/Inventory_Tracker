package com.example.inventoryTracker.DTO.ResponseDTOS;

import com.example.inventoryTracker.Entities.Enums.Roles;

import lombok.Data;

@Data
public class AppUserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private Roles role;
}
