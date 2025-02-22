package com.example.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long ProductId;
    private int quantity;
    private double priceAtTheTimeOfSale;
    private Timestamp deletedAt;
}