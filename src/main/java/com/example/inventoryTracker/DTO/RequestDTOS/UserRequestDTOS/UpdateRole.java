package com.example.inventoryTracker.DTO.RequestDTOS.UserRequestDTOS;

import com.example.inventoryTracker.Entities.Enums.Roles;

import lombok.Data;

@Data
public class UpdateRole {
    private Roles role;
}
