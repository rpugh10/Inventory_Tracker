package com.example.inventoryTracker.DTO.ResponseDTOS;

import lombok.Data;

@Data
public class AppUserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String role;
}
