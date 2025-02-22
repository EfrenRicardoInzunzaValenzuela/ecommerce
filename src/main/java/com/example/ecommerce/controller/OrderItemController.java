package com.example.ecommerce.controller;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateOrderItem;
import com.example.ecommerce.dto.requests.RequestUpdateOrderItem;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public Response getAllOrderItems() throws RepositoryException {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public Response getOrderItemById(@PathVariable Long id) throws RepositoryException {
        return orderItemService.getOrderItemById(id);
    }

    @PostMapping
    public Response createOrderItem(@Valid @RequestBody RequestCreateOrderItem request) throws RepositoryException {
        return orderItemService.createOrderItem(request);
    }

    @PutMapping("/{id}")
    public Response updateOrderItem(@PathVariable Long id, @Valid @RequestBody RequestUpdateOrderItem request) throws RepositoryException {
        return orderItemService.updateOrderItem(id, request);
    }

    @DeleteMapping("/{id}")
    public Response deleteOrderItem(@PathVariable Long id) throws RepositoryException {
        return orderItemService.deleteOrderItem(id);
    }
}