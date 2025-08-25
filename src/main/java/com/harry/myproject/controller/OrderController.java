package com.harry.myproject.controller;

import com.harry.myproject.dto.CreatePrductRequest;
import com.harry.myproject.dto.OrderQueryParams;
import com.harry.myproject.model.Order;
import com.harry.myproject.service.OrderService;
import com.harry.myproject.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam (defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam (defaultValue = "0") Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setOffset(offset);
        orderQueryParams.setLimit(limit);

        // 取得 order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // 取得 order 總數
        Integer count = orderService.getOrdersCount(orderQueryParams);

        // 分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.ok(page);
    }


    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> CreateOreder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreatePrductRequest createPrductRequest) {

        Integer orderId = orderService.createOrder(userId,createPrductRequest);

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(order);

    }
}
