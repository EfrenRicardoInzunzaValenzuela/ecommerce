package com.example.ecommerce.repository;

import com.example.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDeletedAtIsNull();
    Product findFirstById(Long id);
    Product findFirstByIdAndDeletedAtIsNull(Long id);
}