package com.inginterview.storemanagement.controller;

import com.inginterview.storemanagement.entity.Product;
import com.inginterview.storemanagement.model.ProductRequest;
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
@RequestMapping(value = "/products")
public class ProductRestController {

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getById(@PathVariable Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest product) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id,
                       @Valid @RequestBody ProductRequest product) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
