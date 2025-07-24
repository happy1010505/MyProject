package com.harry.myproject.dao.impl;

import com.harry.myproject.dao.ProductDao;
import com.harry.myproject.rowMapper.ProductRowMapper;
import com.harry.myproject.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description,created_date,last_modified_date FROM product WHERE product_id=:id";
        Map<String,Object> map = new HashMap<>();
        map.put("id", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(productList.isEmpty()){
            return null;
        }else{
            return productList.get(0);
        }
    }
}
