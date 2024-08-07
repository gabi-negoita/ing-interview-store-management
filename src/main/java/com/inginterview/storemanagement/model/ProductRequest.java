package com.inginterview.storemanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Stock is required")
    private Integer stock;

    @NotNull(message = "Category is required")
    private String category;
}
