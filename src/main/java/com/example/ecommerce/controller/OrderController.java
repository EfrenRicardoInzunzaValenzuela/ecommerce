package com.example.ecommerce.controller;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateOrder;
import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Response getAllOrders() throws RepositoryException {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Response getOrderById(@PathVariable Long id) throws RepositoryException {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Response createOrder(@Valid @RequestBody RequestCreateOrder request) throws RepositoryException {
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}")
    public Response updateOrder(@PathVariable Long id, @Valid @RequestBody RequestUpdateOrder request) throws RepositoryException {
        return orderService.updateOrder(id, request);
    }

    @DeleteMapping("/{id}")
    public Response deleteOrder(@PathVariable Long id) throws RepositoryException {
        return orderService.deleteOrder(id);
    }
}