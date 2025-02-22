package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.Data;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.TransactionService;
import com.example.ecommerce.dto.requests.RequestCreateOrderItem;
import com.example.ecommerce.dto.requests.RequestUpdateOrderItem;
import com.example.ecommerce.entities.OrderItem;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.model.OrderItemModel;
import com.example.ecommerce.service.OrderItemService;
import com.example.ecommerce.utils.Constants;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Resource(name = "getTransactionService")
    private TransactionService transactionService;

    private final OrderItemModel orderItemModel;

    public OrderItemServiceImpl(OrderItemModel orderItemModel, TransactionService transactionService) {
        this.orderItemModel = orderItemModel;
        this.transactionService = transactionService;
    }

    @Override
    public Response getAllOrderItems() throws RepositoryException {
        Response response = new Response();
        try {
            List<OrderItem> list = getOrderItems();
            if (list == null) {
                throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
            Data data = new Data();
            data.setOrderItemList(list);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response getOrderItemById(Long id) throws RepositoryException {
        Response response = new Response();
        try {
            OrderItem orderItem = getById(id);
            if (orderItem == null) {
                throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_READ_SUCCESS);
            Data data = new Data();
            data.setOrderItem(orderItem);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response createOrderItem(RequestCreateOrderItem request) throws RepositoryException {
        Response response = new Response();
        try {
            OrderItem orderItem = createEntity(request);
            OrderItem insertedOrderItem = insert(orderItem);
            if (insertedOrderItem == null) {
                throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_SAVE_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_SAVE_SUCCESS);
            Data data = new Data();
            data.setOrderItem(insertedOrderItem);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_SAVE_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response updateOrderItem(Long id, RequestUpdateOrderItem request) throws RepositoryException {
        Response response = new Response();
        try {
            OrderItem updatedOrderItem = update(id, request);
            if (updatedOrderItem == null) {
                throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_UPDATE_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_UPDATE_SUCCESS);
            Data data = new Data();
            data.setOrderItem(updatedOrderItem);
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_UPDATE_DATABASE, e);
        }
        return response;
    }

    @Override
    public Response deleteOrderItem(Long id) throws RepositoryException {
        Response response = new Response();
        try {
            OrderItem deletedOrderItem = delete(id);
            if (deletedOrderItem == null) {
                throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_DELETE_DATABASE, "");
            }
            this.transactionService.setMeta(Constants.CODE_SUCCESS, Constants.MSJE_DELETE_SUCCESS);
            Data data = new Data();
            data.setMessage("OrderItem deleted successfully");
            response.setMeta(this.transactionService.getMeta());
            response.setData(data);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_DELETE_DATABASE, e);
        }
        return response;
    }

    private List<OrderItem> getOrderItems() throws RepositoryException {
        List<OrderItem> list;
        try {
            list = this.orderItemModel.getAllOrderItems();
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return list;
    }

    private OrderItem getById(Long id) throws RepositoryException {
        OrderItem element;
        try {
            element = this.orderItemModel.getOrderItemById(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_READ_DATABASE, e);
        }
        return element;
    }

    private OrderItem insert(OrderItem orderItem) throws RepositoryException {
        OrderItem element;
        try {
            element = orderItemModel.createOrderItem(orderItem);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_SAVE_DATABASE, e);
        }
        return element;
    }

    private OrderItem update(Long id, RequestUpdateOrderItem request) throws RepositoryException {
        OrderItem element;
        try {
            element = orderItemModel.updateOrderItem(id, request);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_UPDATE_DATABASE, e);
        }
        return element;
    }

    private OrderItem delete(Long id) throws RepositoryException {
        OrderItem element;
        try {
            element = orderItemModel.deleteOrderItem(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(Constants.CODE_ERROR_SERVICE_INTERNAL, Constants.MSJE_ERROR_DELETE_DATABASE, e);
        }
        return element;
    }

    OrderItem createEntity(RequestCreateOrderItem request) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(request.getOrderId());
        orderItem.setProductId(request.getProductId());
        orderItem.setQuantity(request.getQuantity());
        return orderItem;
    }

}
