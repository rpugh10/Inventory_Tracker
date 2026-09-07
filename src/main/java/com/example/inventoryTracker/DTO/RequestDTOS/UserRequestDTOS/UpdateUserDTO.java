package com.example.inventoryTracker.DTO.RequestDTOS.UserRequestDTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class UpdateUserDTO {

    @NotBlank 
    private String username;
   
    @NotBlank 
    @Email 
    private String email;
}
