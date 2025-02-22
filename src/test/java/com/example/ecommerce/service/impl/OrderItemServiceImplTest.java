package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.Meta;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.TransactionService;
import com.example.ecommerce.dto.requests.RequestCreateOrderItem;
import com.example.ecommerce.dto.requests.RequestUpdateOrderItem;
import com.example.ecommerce.entities.OrderItem;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.model.OrderItemModel;
import com.example.ecommerce.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {

    @Mock
    private OrderItemModel orderItemModel;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private OrderItem orderItem;
    private RequestCreateOrderItem requestCreateOrderItem;
    private RequestUpdateOrderItem requestUpdateOrderItem;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrderId(1L);
        orderItem.setProductId(1L);
        orderItem.setQuantity(10);
        orderItem.getId();

        requestCreateOrderItem = new RequestCreateOrderItem();
        requestCreateOrderItem.setOrderId(1L);
        requestCreateOrderItem.setProductId(1L);
        requestCreateOrderItem.setQuantity(10);

        requestUpdateOrderItem = new RequestUpdateOrderItem();
        requestUpdateOrderItem.setOrderId(1L);
        requestUpdateOrderItem.setProductId(1L);
        requestUpdateOrderItem.setQuantity(20);
    }

    @Test
    void testGetAllOrderItems_Success() throws RepositoryException {
        List<OrderItem> orderItemList = Arrays.asList(orderItem, orderItem);
        when(orderItemModel.getAllOrderItems()).thenReturn(orderItemList);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
        Response response = orderItemService.getAllOrderItems();
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(orderItemList, response.getData().getOrderItemList());
        verify(orderItemModel, times(1)).getAllOrderItems();
    }

    @Test
    void testGetAllOrderItems_EmptyList() throws RepositoryException {
        when(orderItemModel.getAllOrderItems()).thenReturn(Collections.emptyList());
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
        Response response = orderItemService.getAllOrderItems();
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertTrue(response.getData().getOrderItemList().isEmpty());
        verify(orderItemModel, times(1)).getAllOrderItems();
    }

    @Test
    void testGetAllOrderItems_RepositoryException() {
        when(orderItemModel.getAllOrderItems()).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, orderItemService::getAllOrderItems);
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_READ_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).getAllOrderItems();
    }

    @Test
    void testGetOrderItemById_Success() throws RepositoryException {
        when(orderItemModel.getOrderItemById(1L)).thenReturn(orderItem);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
        Response response = orderItemService.getOrderItemById(1L);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(orderItem, response.getData().getOrderItem());
        verify(orderItemModel, times(1)).getOrderItemById(1L);
    }

    @Test
    void testGetOrderItemById_NotFound() {
        when(orderItemModel.getOrderItemById(1L)).thenReturn(null);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.getOrderItemById(1L));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_READ_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).getOrderItemById(1L);
    }

    @Test
    void testGetOrderItemById_RepositoryException() {
        when(orderItemModel.getOrderItemById(1L)).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.getOrderItemById(1L));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_READ_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).getOrderItemById(1L);
    }

    @Test
    void testCreateOrderItem_Success() throws RepositoryException {
        when(orderItemModel.createOrderItem(any(OrderItem.class))).thenReturn(orderItem);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_SAVE_SUCCESS);
        Response response = orderItemService.createOrderItem(requestCreateOrderItem);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(orderItem, response.getData().getOrderItem());
        verify(orderItemModel, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    void testCreateOrderItem_RepositoryException() {
        when(orderItemModel.createOrderItem(any(OrderItem.class))).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.createOrderItem(requestCreateOrderItem));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_SAVE_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    void testUpdateOrderItem_Success() throws RepositoryException {
        when(orderItemModel.updateOrderItem(1L, requestUpdateOrderItem)).thenReturn(orderItem);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_UPDATE_SUCCESS);
        Response response = orderItemService.updateOrderItem(1L, requestUpdateOrderItem);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals(orderItem, response.getData().getOrderItem());
        verify(orderItemModel, times(1)).updateOrderItem(1L, requestUpdateOrderItem);
    }

    @Test
    void testUpdateOrderItem_NotFound() {
        when(orderItemModel.updateOrderItem(1L, requestUpdateOrderItem)).thenReturn(null);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.updateOrderItem(1L, requestUpdateOrderItem));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_UPDATE_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).updateOrderItem(1L, requestUpdateOrderItem);
    }

    @Test
    void testUpdateOrderItem_RepositoryException() {
        when(orderItemModel.updateOrderItem(1L, requestUpdateOrderItem)).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.updateOrderItem(1L, requestUpdateOrderItem));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_UPDATE_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).updateOrderItem(1L, requestUpdateOrderItem);
    }

    @Test
    void testDeleteOrderItem_Success() throws RepositoryException {
        when(orderItemModel.deleteOrderItem(1L)).thenReturn(orderItem);
        when(transactionService.getMeta()).thenReturn(new Meta());
        doNothing().when(transactionService).setMeta(Constants.CODE_SUCCESS, Constants.MSJE_DELETE_SUCCESS);
        Response response = orderItemService.deleteOrderItem(1L);
        assertEquals(Constants.CODE_SUCCESS, response.getMeta().getCodeHttp());
        assertNotNull(response.getData());
        assertEquals("OrderItem deleted successfully", response.getData().getMessage());
        verify(orderItemModel, times(1)).deleteOrderItem(1L);
    }

    @Test
    void testDeleteOrderItem_NotFound() {
        when(orderItemModel.deleteOrderItem(1L)).thenReturn(null);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.deleteOrderItem(1L));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_DELETE_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).deleteOrderItem(1L);
    }

    @Test
    void testDeleteOrderItem_RepositoryException() {
        when(orderItemModel.deleteOrderItem(1L)).thenThrow(new DataAccessException("Database error") {
        });
        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemService.deleteOrderItem(1L));
        assertEquals(Constants.CODE_ERROR_SERVICE_INTERNAL, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_DELETE_DATABASE, exception.getMessage());
        verify(orderItemModel, times(1)).deleteOrderItem(1L);
    }
}