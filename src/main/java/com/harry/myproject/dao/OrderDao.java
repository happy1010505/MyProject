package com.harry.myproject.dao;

import com.harry.myproject.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId,Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem>  orderItemList);
}
