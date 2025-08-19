package com.harry.myproject.controller;

import com.harry.myproject.dto.CreatePrductRequest;
import com.harry.myproject.model.Order;
import com.harry.myproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> CreateOreder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreatePrductRequest createPrductRequest) {

        Integer orderId = orderService.createOrder(userId,createPrductRequest);

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(order);

    }
}
