package com.example.inventoryTracker.DTO.RequestDTOS;

import lombok.Data;

@Data
public class AppUserRequestDTO {

    private String username;
    private String password;
    private String email;
    private String role;
}
