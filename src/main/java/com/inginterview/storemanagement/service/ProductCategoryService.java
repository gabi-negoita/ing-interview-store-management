package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.entity.ProductCategory;
import com.inginterview.storemanagement.exception.ProductCategoryNotFoundException;
import com.inginterview.storemanagement.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category with id " + id + " not found"));
    }

    public ProductCategory getByName(String name) {
        return productCategoryRepository.findByName(name)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category with name " + name + " not found"));
    }
}
