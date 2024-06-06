package com.example.ogani.service;

import java.util.List;
import java.util.Optional;

import com.example.ogani.entity.Order;
import com.example.ogani.model.request.CreateOrderRequest;

public interface OrderService {
    
    void placeOrder(CreateOrderRequest request);

    List<Order> getList();
    
    List<Order> getOrderByUser(String username);

    Optional<Order> findById(Long id);
    void deleteOrder(long id);

}
