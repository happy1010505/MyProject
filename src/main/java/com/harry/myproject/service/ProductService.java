package com.harry.myproject.service;

import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProduct(Integer productId);
    List<Product> getProducts();
}
