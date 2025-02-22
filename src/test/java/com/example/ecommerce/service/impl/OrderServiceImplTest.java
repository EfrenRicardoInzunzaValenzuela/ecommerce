package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.Meta;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.TransactionService;
import com.example.ecommerce.dto.requests.RequestCreateOrder;
import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.model.OrderModel;
import com.example.ecommerce.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderModel orderModel;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private RequestCreateOrder requestCreateOrder;
    private RequestUpdateOrder requestUpdateOrder;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setCustomerEmail("john.doe@example.com");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        order.getId();
        order.getCreatedAt();

        requestCreateOrder = new RequestCreateOrder();
        requestCreateOrder.setCustomerName("Jane Doe");
        requestCreateOrder.setCustomerEmail("jane.doe@example.com");

        requestUpdateOrder = new RequestUpdateOrder();
        requestUpdateOrder.setCustomerName("Updated Name");
        requestUpdateOrder.setCustomerEmail("updated.email@example.com");
    }

    @Test
    void testGetAllOrders_Success() {
        List<Order> orderList = Arrays.asList(order, order);
        when(orderModel.getAllOrders()).thenReturn(orderList);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
        Response response = orderService.getAllOrders();
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(orderList, response.getData().getOrderList());
        verify(orderModel, times(1)).getAllOrders();
    }

    @Test
    void testGetAllOrders_EmptyList() {
        when(orderModel.getAllOrders()).thenReturn(Collections.emptyList());
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
        Response response = orderService.getAllOrders();
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertTrue(response.getData().getOrderList().isEmpty());
        verify(orderModel, times(1)).getAllOrders();
    }

    @Test
    void testGetAllOrders_RepositoryException() {
        when(orderModel.getAllOrders()).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, orderService::getAllOrders);
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_READ_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById_Success() {
        when(orderModel.getOrderById(1L)).thenReturn(order);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
        Response response = orderService.getOrderById(1L);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(order, response.getData().getOrder());
        verify(orderModel, times(1)).getOrderById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderModel.getOrderById(1L)).thenReturn(null);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.getOrderById(1L));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_NOT_FOUND_READ_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).getOrderById(1L);
    }

    @Test
    void testGetOrderById_RepositoryException() {
        when(orderModel.getOrderById(1L)).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.getOrderById(1L));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_READ_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).getOrderById(1L);
    }

    @Test
    void testCreateOrder_Success() {
        when(orderModel.createOrder(any(Order.class))).thenReturn(order);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_SAVE_SUCCESS);
        Response response = orderService.createOrder(requestCreateOrder);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(order, response.getData().getOrder());
        verify(orderModel, times(1)).createOrder(any(Order.class));
    }

    @Test
    void testCreateOrder_RepositoryException() {
        when(orderModel.createOrder(any(Order.class))).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.createOrder(requestCreateOrder));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_SAVE_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).createOrder(any(Order.class));
    }

    @Test
    void testUpdateOrder_Success() {
        when(orderModel.updateOrder(1L, requestUpdateOrder)).thenReturn(order);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_UPDATE_SUCCESS);
        Response response = orderService.updateOrder(1L, requestUpdateOrder);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(order, response.getData().getOrder());
        verify(orderModel, times(1)).updateOrder(1L, requestUpdateOrder);
    }

    @Test
    void testUpdateOrder_NotFound() {
        when(orderModel.updateOrder(1L, requestUpdateOrder)).thenReturn(null);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.updateOrder(1L, requestUpdateOrder));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_NOT_FOUND_READ_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).updateOrder(1L, requestUpdateOrder);
    }

    @Test
    void testUpdateOrder_RepositoryException() {
        when(orderModel.updateOrder(1L, requestUpdateOrder)).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.updateOrder(1L, requestUpdateOrder));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_UPDATE_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).updateOrder(1L, requestUpdateOrder);
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderModel.deleteOrder(1L)).thenReturn(order);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_DELETE_SUCCESS);
        Response response = orderService.deleteOrder(1L);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals("Order deleted successfully", response.getData().getMessage());
        verify(orderModel, times(1)).deleteOrder(1L);
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderModel.deleteOrder(1L)).thenReturn(null);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.deleteOrder(1L));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_NOT_FOUND_READ_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).deleteOrder(1L);
    }

    @Test
    void testDeleteOrder_RepositoryException() {
        when(orderModel.deleteOrder(1L)).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderService.deleteOrder(1L));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_DELETE_DATABASE, exception.getMessage());
        verify(orderModel, times(1)).deleteOrder(1L);
    }
}