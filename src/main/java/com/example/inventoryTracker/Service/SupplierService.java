package com.example.inventoryTracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.SupplierDTO;
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

    public SupplierDTO getSupplierById(Long id) {
        return supplierMapper.toSupplierDTO(supplierRepository.findById(id).orElse(null));
    }   

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toSupplierDTO)
                .toList();
    }

    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        return supplierMapper.toSupplierDTO(supplierRepository.save(supplierMapper.toSupplier(supplierDTO)));
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
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
