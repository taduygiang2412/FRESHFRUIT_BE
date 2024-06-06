package com.example.ogani.controller;

import java.util.List;
import java.util.Optional;

import com.example.ogani.repository.OrderDetailDTO;
import com.example.ogani.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ogani.entity.Order;
import com.example.ogani.model.request.CreateOrderRequest;
import com.example.ogani.model.response.MessageResponse;
import com.example.ogani.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/")
    @Operation(summary="Lấy ra danh sách đặt hàng")
    public ResponseEntity<List<Order>> getList(){
        List<Order> list = orderService.getList();

        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    @Operation(summary="tìm hóa đơn theo id")
    public ResponseEntity<Optional<Order>> findById(@PathVariable long id){
        Optional<Order> order = orderService.findById(id);

        return ResponseEntity.ok(order);
    }


    @GetMapping("/user")
    @Operation(summary="Lấy ra danh sách đặt hàng của người dùng bằng username")
    public ResponseEntity<List<Order>> getListByUser(@RequestParam("username") String username){
        List<Order> list = orderService.getOrderByUser(username);

        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    @Operation(summary="Đặt hàng sản phẩm")
    public ResponseEntity<?> placeOrder(@RequestBody CreateOrderRequest request){

        orderService.placeOrder(request);

        return ResponseEntity.ok(new MessageResponse("Order Placed Successfully!"));
    }
    @GetMapping("/details/{orderId}")
    public List<OrderDetailDTO> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }
}
