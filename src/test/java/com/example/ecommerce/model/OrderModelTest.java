package com.example.ecommerce.model;

import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderModelTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderModel orderModel;

    private Order order;
    private RequestUpdateOrder requestUpdateOrder;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setCustomerEmail("john.doe@example.com");

        requestUpdateOrder = new RequestUpdateOrder();
        requestUpdateOrder.setCustomerName("Jane Doe");
        requestUpdateOrder.setCustomerEmail("jane.doe@example.com");
    }

    @Test
    void testGetAllOrders_Success() {
        List<Order> orderList = Arrays.asList(order, order);
        when(orderRepository.findByDeletedAtIsNull()).thenReturn(orderList);

        List<Order> result = orderModel.getAllOrders();

        assertNotNull(result);
        assertEquals(orderList, result);
        verify(orderRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    void testGetAllOrders_EmptyList() {
        when(orderRepository.findByDeletedAtIsNull()).thenReturn(Collections.emptyList());

        List<Order> result = orderModel.getAllOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findFirstById(1L)).thenReturn(order);

        Order result = orderModel.getOrderById(1L);

        assertNotNull(result);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findFirstById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findFirstById(1L)).thenReturn(null);

        Order result = orderModel.getOrderById(1L);

        assertNull(result);
        verify(orderRepository, times(1)).findFirstById(1L);
    }

    @Test
    void testCreateOrder_Success() {
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderModel.createOrder(order);

        assertNotNull(result);
        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrder_Success() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderModel.updateOrder(1L, requestUpdateOrder);

        assertNotNull(result);
        assertEquals(order, result);
        assertEquals("Jane Doe", result.getCustomerName());
        assertEquals("jane.doe@example.com", result.getCustomerEmail());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrder_NotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderModel.updateOrder(1L, requestUpdateOrder));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_UPDATE_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderModel.deleteOrder(1L);

        assertNotNull(result);
        assertNotNull(result.getDeletedAt());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderModel.deleteOrder(1L));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_UPDATE_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }
}