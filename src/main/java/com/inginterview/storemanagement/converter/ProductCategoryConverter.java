package com.inginterview.storemanagement.converter;

import com.inginterview.storemanagement.entity.ProductCategory;
import com.inginterview.storemanagement.model.ProductCategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryConverter implements Converter<ProductCategoryRequest, ProductCategory> {

    @Override
    public ProductCategory dtoToEntity(ProductCategoryRequest productCategoryRequest) {
        return ProductCategory.builder()
                .name(productCategoryRequest.getName())
                .description(productCategoryRequest.getDescription())
                .build();
    }

    @Override
    public ProductCategoryRequest entityToDto(ProductCategory productCategory) {
        return ProductCategoryRequest.builder()
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }
}
