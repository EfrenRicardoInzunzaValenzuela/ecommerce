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
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class OrderItemModel {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemModel(OrderItemRepository orderItemRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findByDeletedAtIsNull();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findFirstById(id);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        Order order = orderRepository.findFirstByIdAndDeletedAtIsNull(orderItem.getOrderId());
        Product product = productRepository.findFirstByIdAndDeletedAtIsNull(orderItem.getProductId());

        if (order == null) {
            throw new RepositoryException(Constants.CODE_ERROR_REQUEST_INVALID, Constants.MSJE_ERROR_SAVE_DATABASE, "OrderId does not exist");
        }
        if (product == null) {
            throw new RepositoryException(Constants.CODE_ERROR_REQUEST_INVALID, Constants.MSJE_ERROR_SAVE_DATABASE, "ProductId does not exist");
        }
        // Validate availability
        // Deduct quantity from product stock
        // Return inventory in case of a different product
        orderItem.setPriceAtTheTimeOfSale(product.getPrice());
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Long id, RequestUpdateOrderItem orderItem) {
        Order order = orderRepository.findFirstByIdAndDeletedAtIsNull(orderItem.getOrderId());
        Product product = productRepository.findFirstByIdAndDeletedAtIsNull(orderItem.getProductId());
        OrderItem existingOrderItem = orderItemRepository.findFirstByIdAndDeletedAtIsNull(id);

        if (order == null) {
            throw new RepositoryException(Constants.CODE_ERROR_REQUEST_INVALID, Constants.MSJE_ERROR_SAVE_DATABASE, "OrderId does not exist");
        }
        if (product == null) {
            throw new RepositoryException(Constants.CODE_ERROR_REQUEST_INVALID, Constants.MSJE_ERROR_SAVE_DATABASE, "ProductId does not exist");
        }
        if (existingOrderItem == null) {
            throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_NOT_FOUND_READ_DATABASE, "orderItemId does not exist");
        }
        // Validate availability
        // Deduct quantity from product stock
        // Return inventory in case of a different product
        existingOrderItem.setOrderId(orderItem.getOrderId());
        existingOrderItem.setProductId(orderItem.getProductId());
        existingOrderItem.setQuantity(orderItem.getQuantity());
        existingOrderItem.setPriceAtTheTimeOfSale(product.getPrice());
        return orderItemRepository.save(existingOrderItem);
    }

    public OrderItem deleteOrderItem(Long id) {
        OrderItem existingOrderItem = orderItemRepository.findFirstByIdAndDeletedAtIsNull(id);
        if (existingOrderItem == null) {
            throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_ERROR_DELETE_DATABASE, "");
        }
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        existingOrderItem.setDeletedAt(currentTimestamp);
        return orderItemRepository.save(existingOrderItem);
    }
}
