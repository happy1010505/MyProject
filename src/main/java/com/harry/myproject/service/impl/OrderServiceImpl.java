package com.harry.myproject.service.impl;

import com.harry.myproject.dao.OrderDao;
import com.harry.myproject.dao.ProductDao;
import com.harry.myproject.dto.BuyItem;
import com.harry.myproject.dto.CreatePrductRequest;
import com.harry.myproject.model.Order;
import com.harry.myproject.model.OrderItem;
import com.harry.myproject.model.Product;
import com.harry.myproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userid, CreatePrductRequest createPrductRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for(BuyItem buyItem : createPrductRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價錢
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }


        // 創造訂單
        Integer orderId = orderDao.createOrder(userid,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemById(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }
}
