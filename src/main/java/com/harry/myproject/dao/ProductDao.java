package com.harry.myproject.dao;

import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProduct(Integer productId);
    List<Product> getProducts();
}
