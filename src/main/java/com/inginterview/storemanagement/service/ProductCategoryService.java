package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.converter.ProductCategoryConverter;
import com.inginterview.storemanagement.entity.ProductCategory;
import com.inginterview.storemanagement.exception.ProductCategoryAlreadyExistsException;
import com.inginterview.storemanagement.exception.ProductCategoryNotFoundException;
import com.inginterview.storemanagement.model.ProductCategoryRequest;
import com.inginterview.storemanagement.model.ProductCategoryRequestUpdate;
import com.inginterview.storemanagement.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "productCategories")
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryConverter productCategoryConverter;

    private final CacheManager cacheManager;

    @Cacheable(key = "'all'")
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    @Cacheable(key = "#id")
    public ProductCategory getById(Long id) {
        return getAll().stream()
                .filter(productCategory -> productCategory.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category with id " + id + " not found"));
    }

    public ProductCategory getByName(String name) {
        return this.getAll().stream()
                .filter(productCategory -> productCategory.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category with name " + name + " not found"));
    }

    @CacheEvict(allEntries = true)
    public ProductCategory create(ProductCategoryRequest productCategoryRequest) {
        final var exists = productCategoryRepository.existsByName(productCategoryRequest.getName());
        if (exists) {
            throw new ProductCategoryAlreadyExistsException("Product category with name " + productCategoryRequest.getName() + " already exists");
        }

        final var productCategory = productCategoryConverter.dtoToEntity(productCategoryRequest);
        return productCategoryRepository.save(productCategory);
    }

    @CacheEvict(allEntries = true)
    public void update(Long id, ProductCategoryRequestUpdate productCategoryRequestUpdate) {
        final var productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Product category with id " + id + " not found"));

        productCategory.setDescription(productCategoryRequestUpdate.getDescription());

        productCategoryRepository.save(productCategory);
    }

    @CacheEvict(value = "productCategories", allEntries = true)
    public void deleteById(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
