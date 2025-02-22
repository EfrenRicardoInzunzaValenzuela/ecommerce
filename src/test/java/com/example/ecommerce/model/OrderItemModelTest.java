package com.example.ecommerce.model;

import com.example.ecommerce.dto.requests.RequestUpdateOrderItem;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.OrderItem;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
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
public class OrderItemModelTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderItemModel orderItemModel;

    private OrderItem orderItem;
    private Order order;
    private Product product;
    private RequestUpdateOrderItem requestUpdateOrderItem;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrderId(1L);
        orderItem.setProductId(1L);
        orderItem.setQuantity(10);
        orderItem.setPriceAtTheTimeOfSale(100.0);

        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setCustomerEmail("john.doe@example.com");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        requestUpdateOrderItem = new RequestUpdateOrderItem();
        requestUpdateOrderItem.setOrderId(1L);
        requestUpdateOrderItem.setProductId(1L);
        requestUpdateOrderItem.setQuantity(20);
    }

    @Test
    void testGetAllOrderItems_Success() {
        List<OrderItem> orderItemList = Arrays.asList(orderItem, orderItem);
        when(orderItemRepository.findByDeletedAtIsNull()).thenReturn(orderItemList);

        List<OrderItem> result = orderItemModel.getAllOrderItems();

        assertNotNull(result);
        assertEquals(orderItemList, result);
        verify(orderItemRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    void testGetAllOrderItems_EmptyList() {
        when(orderItemRepository.findByDeletedAtIsNull()).thenReturn(Collections.emptyList());

        List<OrderItem> result = orderItemModel.getAllOrderItems();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderItemRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    void testGetOrderItemById_Success() {
        when(orderItemRepository.findFirstById(1L)).thenReturn(orderItem);

        OrderItem result = orderItemModel.getOrderItemById(1L);

        assertNotNull(result);
        assertEquals(orderItem, result);
        verify(orderItemRepository, times(1)).findFirstById(1L);
    }

    @Test
    void testGetOrderItemById_NotFound() {
        when(orderItemRepository.findFirstById(1L)).thenReturn(null);

        OrderItem result = orderItemModel.getOrderItemById(1L);

        assertNull(result);
        verify(orderItemRepository, times(1)).findFirstById(1L);
    }

    @Test
    void testCreateOrderItem_Success() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(productRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(product);
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem result = orderItemModel.createOrderItem(orderItem);

        assertNotNull(result);
        assertEquals(orderItem, result);
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(productRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, times(1)).save(orderItem);
    }

    @Test
    void testCreateOrderItem_OrderNotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemModel.createOrderItem(orderItem));
        assertEquals(Constants.CODE_ERROR_REQUEST_INVALID, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_SAVE_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }

    @Test
    void testCreateOrderItem_ProductNotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(productRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemModel.createOrderItem(orderItem));
        assertEquals(Constants.CODE_ERROR_REQUEST_INVALID, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_SAVE_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(productRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }

    @Test
    void testUpdateOrderItem_Success() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(productRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(product);
        when(orderItemRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(orderItem);
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem result = orderItemModel.updateOrderItem(1L, requestUpdateOrderItem);

        assertNotNull(result);
        assertEquals(orderItem, result);
        assertEquals(1L, result.getOrderId());
        assertEquals(1L, result.getProductId());
        assertEquals(20, result.getQuantity());
        assertEquals(100.0, result.getPriceAtTheTimeOfSale());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(productRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, times(1)).save(orderItem);
    }

    @Test
    void testUpdateOrderItem_OrderNotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemModel.updateOrderItem(1L, requestUpdateOrderItem));
        assertEquals(Constants.CODE_ERROR_REQUEST_INVALID, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_SAVE_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }

    @Test
    void testUpdateOrderItem_ProductNotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(productRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemModel.updateOrderItem(1L, requestUpdateOrderItem));
        assertEquals(Constants.CODE_ERROR_REQUEST_INVALID, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_SAVE_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(productRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }

    @Test
    void testUpdateOrderItem_OrderItemNotFound() {
        when(orderRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(order);
        when(productRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(product);
        when(orderItemRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemModel.updateOrderItem(1L, requestUpdateOrderItem));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_NOT_FOUND_READ_DATABASE, exception.getMessage());
        verify(orderRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(productRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }

    @Test
    void testDeleteOrderItem_Success() {
        when(orderItemRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(orderItem);
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem result = orderItemModel.deleteOrderItem(1L);

        assertNotNull(result);
        assertNotNull(result.getDeletedAt());
        verify(orderItemRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, times(1)).save(orderItem);
    }

    @Test
    void testDeleteOrderItem_NotFound() {
        when(orderItemRepository.findFirstByIdAndDeletedAtIsNull(1L)).thenReturn(null);

        RepositoryException exception = assertThrows(RepositoryException.class, () -> orderItemModel.deleteOrderItem(1L));
        assertEquals(Constants.CODE_ERROR_NOT_FOUND_INFO, exception.getCode());
        assertEquals(Constants.MSJE_ERROR_DELETE_DATABASE, exception.getMessage());
        verify(orderItemRepository, times(1)).findFirstByIdAndDeletedAtIsNull(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }
}