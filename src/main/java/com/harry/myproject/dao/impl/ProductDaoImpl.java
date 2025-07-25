package com.harry.myproject.dao.impl;

import com.harry.myproject.dao.ProductDao;
import com.harry.myproject.dto.ProductQueryParam;
import com.harry.myproject.dto.ProductRequest;
import com.harry.myproject.model.Product;
import com.harry.myproject.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description," +
                     "created_date,last_modified_date FROM product WHERE product_id=:id";
        Map<String,Object> map = new HashMap<>();
        map.put("id", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(productList.isEmpty()){
            return null;
        }else{
            return productList.get(0);
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name,category,image_url,price,stock,description,created_date,last_modified_date ) " +
                     "VALUES (:productName,:category,:imageUrl,:price,:stock,:description,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProduct(Integer productId,ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName,category = :category,image_url = :imageUrl," +
                     "price = :price,stock = :stock,description = :description,last_modified_date = :lastModifiedDate WHERE product_id = :productId ";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description,"+
                     "created_date,last_modified_date FROM product WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();

        if(productQueryParam.getProductCategory() != null){
            sql += " AND category = :category";
            map.put("category", productQueryParam.getProductCategory().name());
        }
        if(productQueryParam.getSearch() != null){
            sql += " AND product_name LIKE :search";
            map.put("search", "%" +productQueryParam.getSearch()+ "%");
        }
        sql += " ORDER BY "+productQueryParam.getOrderBy()+" " + productQueryParam.getSort();

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        return productList;
    }
}
