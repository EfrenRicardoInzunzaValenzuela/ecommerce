package com.example.ecommerce.model;

import com.example.ecommerce.dto.requests.RequestUpdateProduct;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.utils.Constants;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ProductModel {

    private final ProductRepository productRepository;

    public ProductModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() throws DataAccessException {
        return productRepository.findByDeletedAtIsNull();
    }

    public Product getProductById(Long id) throws DataAccessException {
        return productRepository.findFirstById(id);
    }

    public Product createProduct(Product product) throws DataAccessException {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, RequestUpdateProduct request) throws DataAccessException {
        Product existingProduct = productRepository.findFirstByIdAndDeletedAtIsNull(id);
        if (existingProduct == null) {
            throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_ERROR_UPDATE_DATABASE, "");
        }
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        return productRepository.save(existingProduct);
    }

    public Product deleteProduct(Long id) throws DataAccessException {
        Product existingProduct = productRepository.findFirstByIdAndDeletedAtIsNull(id);
        if (existingProduct == null) {
            throw new RepositoryException(Constants.CODE_ERROR_NOT_FOUND_INFO, Constants.MSJE_ERROR_DELETE_DATABASE, "");
        }
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        existingProduct.setDeletedAt(currentTimestamp);
        return productRepository.save(existingProduct);
    }

}
