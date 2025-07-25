package com.harry.myproject.service;

import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;

public interface ProductService {
    Product getProduct(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
