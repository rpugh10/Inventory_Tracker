package com.example.inventoryTracker.DTO.RequestDTOS;

import com.example.inventoryTracker.Entities.Enums.Roles;

import lombok.Data;

@Data
public class AppUserRequestDTO {

    private String username;
    private String password;
    private String email;
    private Roles role;
}
