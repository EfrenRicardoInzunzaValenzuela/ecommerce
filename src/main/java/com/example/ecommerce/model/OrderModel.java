package com.example.ecommerce.model;

import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.utils.Constants;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class OrderModel {

    private final OrderRepository orderRepository;

    public OrderModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findByDeletedAtIsNull();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findFirstById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, RequestUpdateOrder request) {
        Order existingOrder = orderRepository.findFirstByIdAndDeletedAtIsNull(id);
        if (existingOrder == null) {
            throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_ERROR_UPDATE_DATABASE, "");
        }
        existingOrder.setCustomerName(request.getCustomerName());
        existingOrder.setCustomerEmail(request.getCustomerEmail());
        return orderRepository.save(existingOrder);
    }

    public Order deleteOrder(Long id) {
        Order existingOrder = orderRepository.findFirstByIdAndDeletedAtIsNull(id);
        if (existingOrder == null) {
            throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_ERROR_UPDATE_DATABASE, "");
        }
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        existingOrder.setDeletedAt(currentTimestamp);
        return orderRepository.save(existingOrder);
    }
}
