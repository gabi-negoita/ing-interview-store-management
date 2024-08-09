package com.inginterview.storemanagement.service;

import com.inginterview.storemanagement.entity.Product;
import com.inginterview.storemanagement.entity.ProductCategory;
import com.inginterview.storemanagement.exception.ProductCategoryNotFoundException;
import com.inginterview.storemanagement.exception.ProductNotFoundException;
import com.inginterview.storemanagement.model.ProductRequest;
import com.inginterview.storemanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductCategoryService categoryService;

    @Autowired
    ProductService productService;

    @Test
    void whenGetAll_thenReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(getMockedProducts());

        final var actual = productService.getAll();

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).usingRecursiveComparison().isEqualTo(getMockedProducts());
    }

    @Test
    void whenProductNotFound_thenServiceThrowException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getById(1L));
    }

    @Test
    void whenCreateProduct_thenProductCreatedSuccessfully() {
        when(categoryService.getByName(anyString())).thenReturn(getMockedProductCategory());
        when(productRepository.save(any(Product.class))).thenReturn(getMockedProduct());

        final var product = productService.create(getMockedProductRequest());

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("product 1");
        assertThat(product.getDescription()).isEqualTo("this is product 1");
        assertThat(product.getStock()).isEqualTo(100);
        assertThat(product.getPrice()).isEqualTo(50.0);
        assertThat(product.getCategory()).usingRecursiveComparison().isEqualTo(getMockedProductCategory());
    }

    @Test
    void whenCreateProduct_ifCategoryDoesNotExist_thenServiceThrowsException() {
        when(categoryService.getByName(anyString())).thenThrow(new ProductCategoryNotFoundException("test"));

        assertThrows(ProductCategoryNotFoundException.class, () -> productService.create(getMockedProductRequest()));
    }

    @Test
    void whenUpdateProduct_thenProductUpdatedSuccessfully() {
        when(categoryService.getByName(anyString())).thenReturn(getMockedProductCategory());
        when(productRepository.save(any(Product.class))).thenReturn(getMockedProduct());
        when(productRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> productService.update(1L, getMockedProductRequest()));
    }

    @Test
    void whenUpdateProduct_ifProductDoesNotExist_thenServiceThrowsException() {
        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.update(1L, getMockedProductRequest()));
    }

    @Test
    void whenUpdateProduct_ifCategoryDoesNotExist_thenServiceThrowsException() {
        when(productRepository.existsById(anyLong())).thenReturn(true);
        when(categoryService.getByName(anyString())).thenThrow(new ProductCategoryNotFoundException("test"));

        assertThrows(ProductCategoryNotFoundException.class, () -> productService.update(1L, getMockedProductRequest()));
    }

    @Test
    void whenDeleteProduct_thenProductDeletedSuccessfully() {
        doNothing().when(productRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> productService.deleteById(1L));
    }

    List<Product> getMockedProducts() {
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("product 1")
                        .description("this is product 1")
                        .stock(100)
                        .price(50.0)
                        .category(ProductCategory.builder()
                                .id(1L)
                                .name("category 1")
                                .description("this is category 1")
                                .build())
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("product 2")
                        .description("this is product 2")
                        .stock(100)
                        .price(50.0)
                        .category(ProductCategory.builder()
                                .id(2L)
                                .name("category 2")
                                .description("this is category 2")
                                .build())
                        .build());
    }

    Product getMockedProduct() {
        return getMockedProducts().get(0);
    }

    ProductCategory getMockedProductCategory() {
        return ProductCategory.builder()
                .id(1L)
                .name("category 1")
                .description("this is category 1")
                .build();
    }

    ProductRequest getMockedProductRequest() {
        return ProductRequest.builder()
                .name("product 1")
                .description("this is product 1")
                .stock(100)
                .price(50.0)
                .category("category 1")
                .build();
    }
}
