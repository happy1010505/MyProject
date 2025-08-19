package com.harry.myproject.dao.impl;

import com.harry.myproject.dao.OrderDao;
import com.harry.myproject.model.OrderItem;
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
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id,total_amount,created_date,last_modified_date) VALUES (:userid,:totalamount,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userId);
        map.put("totalamount",totalAmount);
        Date date = new Date();
        map.put("createdDate",date);
        map.put("lastModifiedDate",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        Integer orderId = keyHolder.getKey().intValue();
        return orderId;

    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id,product_id,quantity,amount) VALUES (:orderId,:productId,:quantity,:amount) ";

        orderItemList.stream().forEach(orderItem -> {
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            map.put("productId",orderItem.getProductId());
            map.put("quantity",orderItem.getQuantity());
            map.put("amount",orderItem.getAmount());
            namedParameterJdbcTemplate.update(sql,map);
        });
    }
}
