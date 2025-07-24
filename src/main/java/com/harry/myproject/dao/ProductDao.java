package com.harry.myproject.dao;

import com.harry.myproject.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
