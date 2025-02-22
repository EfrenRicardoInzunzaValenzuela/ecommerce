package com.example.ecommerce.controller;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.requests.RequestCreateProduct;
import com.example.ecommerce.dto.requests.RequestUpdateProduct;
import com.example.ecommerce.exception.RepositoryException;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productServices) {
        this.productService = productServices;
    }

    @GetMapping
    public Response getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Response getProductById(@PathVariable Long id) throws RepositoryException {
        return productService.getProductById(id);
    }

    @PostMapping
    public Response createProduct(@Valid @RequestBody RequestCreateProduct request) throws RepositoryException {
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public Response updateProduct(@PathVariable Long id, @Valid @RequestBody RequestUpdateProduct request) throws RepositoryException {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable Long id) throws RepositoryException {
        return productService.deleteProduct(id);
    }
}