package com.example.ogani.service.impl;

import java.util.List;
import java.util.Optional;


import com.example.ogani.entity.*;
import com.example.ogani.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ogani.exception.NotFoundException;
import com.example.ogani.model.request.CreateOrderDetailRequest;
import com.example.ogani.model.request.CreateOrderRequest;
import com.example.ogani.repository.OrderDetailRepository;
import com.example.ogani.repository.OrderRepository;
import com.example.ogani.repository.UserRepository;
import com.example.ogani.service.OrderService;
import com.example.ogani.service.CouponService;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;



    @Override
    public void placeOrder(CreateOrderRequest request) {
        // TODO Auto-generated method stub
        Order order = new Order();
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + request.getUsername()));
        Coupon coupon = couponRepository.findByCode(request.getCode()).orElseThrow(() -> new NotFoundException("Not Found Coupon"));
        order.setFirstname(request.getFirstname());
        order.setLastname(request.getLastname());
        order.setCountry(request.getCountry());
        order.setAddress(request.getAddress());
        order.setTown(request.getTown());
        order.setState(request.getState());
        order.setPostCode(request.getPostCode());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setNote(request.getNote());


        orderRepository.save(order);

        long totalPrice = 0;
        for(CreateOrderDetailRequest rq: request.getOrderDetails()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(rq.getName());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setQuantity(rq.getQuantity());
            orderDetail.setSubTotal(rq.getPrice()* rq.getQuantity());
            orderDetail.setOrder(order);
            totalPrice += orderDetail.getSubTotal();
            orderDetailRepository.save(orderDetail);
            
        }

        String couponCode = request.getCode();
        if (couponCode != null && ! couponCode.isEmpty()) {
            totalPrice = couponService.cacuCouponValue(couponCode, totalPrice);
        }

        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order.setCoupon(coupon);
        orderRepository.save(order);
//        + request.getCode())
    }



    @Override
    public List<Order> getList() {
        return orderRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Order> getOrderByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User With Username:" + username));

        List<Order> orders = orderRepository.getOrderByUser(user.getId());
        return orders;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);

    }

    @Override
    public void deleteOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
//        order.getImages().remove(this);
        orderRepository.delete(order.get());
    }

}
