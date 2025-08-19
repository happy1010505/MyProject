package com.harry.myproject.service;

import com.harry.myproject.dto.CreatePrductRequest;

public interface OrderService {
    Integer createOrder(Integer userid, CreatePrductRequest createPrductRequest);
}
