package com.inginterview.storemanagement.repository;

import com.inginterview.storemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
