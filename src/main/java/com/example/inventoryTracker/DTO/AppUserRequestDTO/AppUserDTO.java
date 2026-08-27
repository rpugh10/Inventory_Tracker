package com.example.inventoryTracker.DTO.AppUserRequestDTO;

import lombok.Data;

@Data
public class AppUserDTO {

    private String username;
    private String password;
    private String email;
    private String role;
}
