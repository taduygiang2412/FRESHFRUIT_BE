package com.example.ogani.controller;

import com.example.ogani.entity.OrderDetail;
import com.example.ogani.repository.OrderDetailDTO;
import com.example.ogani.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

//    @GetMapping("/details/{orderId}")
//    public List<OrderDetailDTO> getOrderDetailsByOrderId(@PathVariable Long orderId) {
//        return orderDetailService.getOrderDetailsByOrderId(orderId);
//    }
}
