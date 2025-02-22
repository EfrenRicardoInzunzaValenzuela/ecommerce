package com.example.ecommerce.service;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateProduct;
import com.example.ecommerce.dto.requests.RequestUpdateProduct;
import com.example.ecommerce.exception.RepositoryException;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Response getAllProducts() throws RepositoryException;
    Response getProductById(Long id) throws RepositoryException;
    Response createProduct(RequestCreateProduct request) throws RepositoryException;
    Response updateProduct(Long id, RequestUpdateProduct request) throws RepositoryException;
    Response deleteProduct(Long id) throws RepositoryException;
}
