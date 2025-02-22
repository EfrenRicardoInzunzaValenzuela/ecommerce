package com.example.ecommerce.service;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateOrderItem;
import com.example.ecommerce.dto.requests.RequestUpdateOrderItem;
import com.example.ecommerce.exception.RepositoryException;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    Response getAllOrderItems() throws RepositoryException;
    Response getOrderItemById(Long id) throws RepositoryException;
    Response createOrderItem(RequestCreateOrderItem orderItem) throws RepositoryException;
    Response updateOrderItem(Long id, RequestUpdateOrderItem orderItem) throws RepositoryException;
    Response deleteOrderItem(Long id) throws RepositoryException;
}