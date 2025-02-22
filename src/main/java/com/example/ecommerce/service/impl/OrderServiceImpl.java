package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.Data;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.TransactionService;
import com.example.ecommerce.dto.requests.RequestCreateOrder;
import com.example.ecommerce.dto.requests.RequestUpdateOrder;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.model.OrderModel;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.utils.Constants;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource(name = "getTransactionService")
    private TransactionService transactionService;

    private final OrderModel orderModel;

    public OrderServiceImpl(OrderModel orderModel, TransactionService transactionService) {
        this.orderModel = orderModel;
        this.transactionService = transactionService;
    }

    @Override
    public Response getAllOrders() {
        Response response = new Response();
        try {
            List<Order> list = getOrders();
            if (list == null) {
                throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
            Data data = new Data();
            data.setOrderList(list);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response getOrderById(Long id) {
        Response response = new Response();
        try {
            Order order = getById(id);
            if (order == null) {
                throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_NOT_FOUND_READ_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
            Data data = new Data();
            data.setOrder(order);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response createOrder(RequestCreateOrder request) {
        Response response = new Response();
        try {
            Order order = createEntity(request);
            Order insertedOrder = insert(order);
            if (insertedOrder == null) {
                throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_ERROR_SAVE_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_SAVE_SUCCESS);
            Data data = new Data();
            data.setOrder(insertedOrder);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_SAVE_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response updateOrder(Long id, RequestUpdateOrder request) {
        Response response = new Response();
        try {
            Order updatedOrder = update(id, request);
            if (updatedOrder == null) {
                throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_NOT_FOUND_READ_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_UPDATE_SUCCESS);
            Data data = new Data();
            data.setOrder(updatedOrder);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_UPDATE_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response deleteOrder(Long id) {
        Response response = new Response();
        try {
            Order deletedOrder = delete(id);
            if (deletedOrder == null) {
                throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_NOT_FOUND_READ_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_DELETE_SUCCESS);
            Data data = new Data();
            data.setMessage("Order deleted successfully");
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_DELETE_DATABASE, e);
        }
        return response;
    }


    private List<Order> getOrders() throws RepositoryException {
        List<Order> list;
        try {
            list = this.orderModel.getAllOrders();
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return list;
    }

    private Order getById(Long id) throws RepositoryException {
        Order element;
        try {
            element = this.orderModel.getOrderById(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return element;
    }

    private Order insert(Order order) throws RepositoryException {
        Order element;
        try {
            element = orderModel.createOrder(order);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_SAVE_DATABASE, e);
        }
        return element;
    }

    private Order update(Long id, RequestUpdateOrder request) throws RepositoryException {
        Order element;
        try {
            element = orderModel.updateOrder(id, request);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_UPDATE_DATABASE, e);
        }
        return element;
    }

    private Order delete(Long id) throws RepositoryException {
        Order element;
        try {
            element = orderModel.deleteOrder(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_DELETE_DATABASE, e);
        }
        return element;
    }

    Order createEntity(RequestCreateOrder request) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCreatedAt(currentTimestamp);
        return order;
    }

}
