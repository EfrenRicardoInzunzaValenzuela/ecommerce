package com.example.ecommerce.repository;

import com.example.ecommerce.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByDeletedAtIsNull();
    OrderItem findFirstById(Long id);
    OrderItem findFirstByIdAndDeletedAtIsNull(Long id);
}
