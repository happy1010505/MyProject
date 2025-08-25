package com.harry.myproject.service;

import com.harry.myproject.dto.CreatePrductRequest;
import com.harry.myproject.dto.OrderQueryParams;
import com.harry.myproject.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer getOrdersCount(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userid, CreatePrductRequest createPrductRequest);

    Order getOrderById(Integer orderId);
}
