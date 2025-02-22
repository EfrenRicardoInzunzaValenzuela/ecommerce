package com.example.ecommerce.controller;

import com.example.ecommerce.dto.Meta;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateOrderItem;
import com.example.ecommerce.dto.requests.RequestUpdateOrderItem;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.service.OrderItemService;
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
class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

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
    void testGetAllOrderItems() throws RepositoryException {
        when(orderItemService.getAllOrderItems()).thenReturn(mockResponse);
        Response response = orderItemController.getAllOrderItems();
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testGetOrderItemById() throws RepositoryException {
        Long orderItemId = 1L;
        when(orderItemService.getOrderItemById(orderItemId)).thenReturn(mockResponse);
        Response response = orderItemController.getOrderItemById(orderItemId);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testCreateOrderItem() throws RepositoryException {
        RequestCreateOrderItem request = new RequestCreateOrderItem();
        when(orderItemService.createOrderItem(request)).thenReturn(mockResponse);
        Response response = orderItemController.createOrderItem(request);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testUpdateOrderItem() throws RepositoryException {
        Long orderItemId = 1L;
        RequestUpdateOrderItem request = new RequestUpdateOrderItem();
        when(orderItemService.updateOrderItem(orderItemId, request)).thenReturn(mockResponse);
        Response response = orderItemController.updateOrderItem(orderItemId, request);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }

    @Test
    void testDeleteOrderItem() throws RepositoryException {
        Long orderItemId = 1L;
        when(orderItemService.deleteOrderItem(orderItemId)).thenReturn(mockResponse);
        Response response = orderItemController.deleteOrderItem(orderItemId);
        assertEquals(HttpStatus.OK.value(), response.getMeta().getCodeHttp());
    }
}