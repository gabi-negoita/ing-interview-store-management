package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.entity.ProductCategory;
import com.inginterview.storemanagement.model.ProductCategoryRequest;
import com.inginterview.storemanagement.model.ProductCategoryRequestUpdate;
import com.inginterview.storemanagement.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product-categories")
public class ProductCategoryRestController {

    private final ProductCategoryService productCategoryService;

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCategory> getAll() {
        return productCategoryService.getAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductCategory getById(@PathVariable Long id) {
        return productCategoryService.getById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ProductCategory getByName(@PathVariable String name) {
        return productCategoryService.getByName(name);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCategory create(@Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        return productCategoryService.create(productCategoryRequest);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id,
                       @Valid @RequestBody ProductCategoryRequestUpdate productCategoryRequestUpdate) {
        productCategoryService.update(id, productCategoryRequestUpdate);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productCategoryService.deleteById(id);
    }
}
