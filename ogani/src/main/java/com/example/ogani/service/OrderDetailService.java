package com.example.ogani.service;

import com.example.ogani.entity.OrderDetail;
import com.example.ogani.repository.OrderDetailDTO;
import com.example.ogani.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    public List<OrderDetailDTO> getOrderDetailsByOrderId(Long orderId);


}
