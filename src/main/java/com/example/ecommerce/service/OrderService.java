package com.example.ecommerce.service;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateOrder;
import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.exception.RepositoryException;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Response getAllOrders() throws RepositoryException;
    Response getOrderById(Long id) throws RepositoryException;
    Response createOrder(RequestCreateOrder request) throws RepositoryException;
    Response updateOrder(Long id, RequestUpdateOrder request) throws RepositoryException;
    Response deleteOrder(Long id) throws RepositoryException;
}
