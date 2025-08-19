package com.harry.myproject.service;

import com.harry.myproject.dto.CreatePrductRequest;
import com.harry.myproject.model.Order;

public interface OrderService {
    Integer createOrder(Integer userid, CreatePrductRequest createPrductRequest);

    Order getOrderById(Integer orderId);
}
