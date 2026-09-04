package com.example.inventoryTracker.DTO.RequestDTOS.UserRequestDTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppUserLoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
