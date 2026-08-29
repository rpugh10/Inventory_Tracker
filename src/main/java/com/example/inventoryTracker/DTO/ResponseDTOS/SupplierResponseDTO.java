package com.example.inventoryTracker.DTO.ResponseDTOS;

import lombok.Data;

@Data
public class SupplierResponseDTO {

    private Long id;
    private String supplierName;
    private String email;
    private String phoneNumber;
}
