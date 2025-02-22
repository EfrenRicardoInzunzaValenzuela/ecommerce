package com.example.ecommerce.dto;

import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.OrderItem;
import com.example.ecommerce.entities.Product;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {
    private Product product;
    private List<Product> productsList;
    private Order order;
    private List<Order> orderList;
    private OrderItem orderItem;
    private List<OrderItem> orderItemList;
    private String message;
}

