package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.converter.ProductConverter;
import com.inginterview.storemanagement.entity.Product;
import com.inginterview.storemanagement.exception.ProductNotFoundException;
import com.inginterview.storemanagement.model.ProductRequest;
import com.inginterview.storemanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService categoryService;
    private final ProductConverter productConverter;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    public Product create(ProductRequest productRequest) {
        final var category = categoryService.getByName(productRequest.getCategory());
        final var product = productConverter.dtoToEntity(productRequest);

        product.setCategory(category);

        return productRepository.save(product);
    }

    public void update(Long id, ProductRequest productRequest) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        final var category = categoryService.getByName(productRequest.getCategory());
        final var product = productConverter.dtoToEntity(productRequest);

        product.setId(id);
        product.setCategory(category);

        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
