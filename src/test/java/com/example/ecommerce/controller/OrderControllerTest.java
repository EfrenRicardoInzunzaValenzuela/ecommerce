package com.example.ecommerce.controller;

import com.example.ecommerce.dto.Meta;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateOrder;
import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Response mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new Response();
        Meta meta = new Meta();
        meta.setCodeHttp(HttpStatus.OK.value());
        mockResponse.setMeta(meta);
        mockResponse.setData(null);
    }

    @Test
    void testGetAllOrders() throws RepositoryException {
        when(orderService.getAllOrders()).thenReturn(mockResponse);
        Response response = orderController.getAllOrders();
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testGetOrderById() throws RepositoryException {
        Long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenReturn(mockResponse);
        Response response = orderController.getOrderById(orderId);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testCreateOrder() throws RepositoryException {
        RequestCreateOrder request = new RequestCreateOrder();
        when(orderService.createOrder(request)).thenReturn(mockResponse);
        Response response = orderController.createOrder(request);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testUpdateOrder() throws RepositoryException {
        Long orderId = 1L;
        RequestUpdateOrder request = new RequestUpdateOrder();
        when(orderService.updateOrder(orderId, request)).thenReturn(mockResponse);
        Response response = orderController.updateOrder(orderId, request);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testDeleteOrder() throws RepositoryException {
        Long orderId = 1L;
        when(orderService.deleteOrder(orderId)).thenReturn(mockResponse);
        Response response = orderController.deleteOrder(orderId);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }
}