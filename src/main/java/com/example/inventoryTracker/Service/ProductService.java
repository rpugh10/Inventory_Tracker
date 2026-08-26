package com.example.inventoryTracker.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.ProductDTO;
import com.example.inventoryTracker.Entities.Product;
import com.example.inventoryTracker.Mapper.ProductMapper;
import com.example.inventoryTracker.Repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO findProductById(Long id) {
        return productRepository.findById(id) //Don't need to use .stream() because .findById returns an optional.
                .map(productMapper::toProductDTO)
                .orElseGet(() -> {
                    throw new RuntimeException("Product not found with id: " + id);
                });
    }

    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream() //Have to use .stream() because .findAll() returns a List
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDTO(savedProduct);
        
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setUnit(productDTO.getUnit());
            existingProduct.setSku(productDTO.getSku());
            existingProduct.setReorderLevel(productDTO.getReorderLevel());
            existingProduct.setUnitCost(productDTO.getUnitCost());
            Product updatedProduct = productRepository.save(existingProduct);
            return productMapper.toProductDTO(updatedProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public void deleteProduct(Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    
}
