package com.inginterview.storemanagement.converter;

import com.inginterview.storemanagement.entity.Product;
import com.inginterview.storemanagement.model.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<ProductRequest, Product> {

    @Override
    public Product dtoToEntity(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();
    }

    @Override
    public ProductRequest entityToDto(Product product) {
        return ProductRequest.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
