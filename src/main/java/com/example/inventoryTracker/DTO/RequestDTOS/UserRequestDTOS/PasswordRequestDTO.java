package com.example.inventoryTracker.DTO.RequestDTOS.UserRequestDTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRequestDTO {
    
    @NotBlank
    private String newPassword;
}
