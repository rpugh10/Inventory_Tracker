package com.example.inventoryTracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.RequestDTOS.SupplierRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.SupplierResponseDTO;
import com.example.inventoryTracker.Mapper.SupplierMapper;
import com.example.inventoryTracker.Repository.SupplierRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public SupplierResponseDTO getSupplierById(Long id) {
        return supplierMapper.toSupplierDTO(supplierRepository.findById(id).orElse(null));
    }   

    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toSupplierDTO)
                .toList();
    }

    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierDTO) {
        return supplierMapper.toSupplierDTO(supplierRepository.save(supplierMapper.toSupplier(supplierDTO)));
    }

    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierDTO) {
        return supplierRepository.findById(id)
                .map(existingSupplier -> {
                    existingSupplier.setSupplierName(supplierDTO.getSupplierName());
                    existingSupplier.setEmail(supplierDTO.getEmail());
                    existingSupplier.setPhoneNumber(supplierDTO.getPhoneNumber());
                    return supplierMapper.toSupplierDTO(supplierRepository.save(existingSupplier));
                })
                .orElse(null);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
