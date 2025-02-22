package com.example.ecommerce.repository;

import com.example.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDeletedAtIsNull();
    Order findFirstById(Long id);
    Order findFirstByIdAndDeletedAtIsNull(Long id);
}
