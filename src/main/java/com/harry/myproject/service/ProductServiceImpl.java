package com.harry.myproject.service;

import com.harry.myproject.dao.ProductDao;
import com.harry.myproject.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProduct(Integer productId) {
        return productDao.getProductById(productId);
    }
}
