package com.harry.myproject.service;

import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
}
