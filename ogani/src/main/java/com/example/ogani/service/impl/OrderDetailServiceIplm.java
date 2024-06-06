package com.example.ogani.service.impl;

import com.example.ogani.entity.Order;
import com.example.ogani.entity.OrderDetail;
import com.example.ogani.repository.OrderDetailDTO;
import com.example.ogani.repository.OrderDetailRepository;
import com.example.ogani.repository.OrderRepository;
import com.example.ogani.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceIplm implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetailDTO> getOrderDetailsByOrderId(Long orderId) {

        return orderDetailRepository.findOrderDetailsByOrderId(orderId);
    }

}
