package com.harry.myproject.service.impl;

import com.harry.myproject.dao.OrderDao;
import com.harry.myproject.dao.ProductDao;
import com.harry.myproject.dao.UserDao;
import com.harry.myproject.dto.BuyItem;
import com.harry.myproject.dto.CreatePrductRequest;
import com.harry.myproject.dto.OrderQueryParams;
import com.harry.myproject.model.Order;
import com.harry.myproject.model.OrderItem;
import com.harry.myproject.model.Product;
import com.harry.myproject.model.User;
import com.harry.myproject.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public Integer createOrder(Integer userid, CreatePrductRequest createPrductRequest) {

        // 檢查 user 是否存在
        User user = userDao.getUserById(userid);
        if(user == null){
            log.warn("該 userId {} 不存在", userid);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for(BuyItem buyItem : createPrductRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if(product == null){
                log.warn("商品 {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if (buyItem.getQuantity() > product.getStock()){
                log.warn("商品 {} 庫存不足, 無法購買，剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            // 扣除商品庫存
            productDao.updateStock(buyItem.getProductId(),product.getStock() - buyItem.getQuantity());

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

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemById(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Integer getOrdersCount(OrderQueryParams orderQueryParams) {
        return orderDao.getOrderCount(orderQueryParams);
    }
}
