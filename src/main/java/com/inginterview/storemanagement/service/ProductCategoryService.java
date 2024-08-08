package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.converter.ProductCategoryConverter;
import com.inginterview.storemanagement.entity.ProductCategory;
import com.inginterview.storemanagement.exception.ProductCategoryAlreadyExistsException;
import com.inginterview.storemanagement.exception.ProductCategoryNotFoundException;
import com.inginterview.storemanagement.model.ProductCategoryRequest;
import com.inginterview.storemanagement.model.ProductCategoryRequestUpdate;
import com.inginterview.storemanagement.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryConverter productCategoryConverter;

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

    public ProductCategory create(ProductCategoryRequest productCategoryRequest) {
        final var exists = productCategoryRepository.existsByName(productCategoryRequest.getName());
        if (exists) {
            throw new ProductCategoryAlreadyExistsException("Product category with name " + productCategoryRequest.getName() + " already exists");
        }

        final var productCategory = productCategoryConverter.dtoToEntity(productCategoryRequest);
        return productCategoryRepository.save(productCategory);
    }

    public void update(Long id, ProductCategoryRequestUpdate productCategoryRequestUpdate) {
        final var productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category with id " + id + " not found"));

        productCategory.setDescription(productCategoryRequestUpdate.getDescription());

        productCategoryRepository.save(productCategory);
    }

    public void deleteById(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
