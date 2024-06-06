package com.example.ogani.repository;

import com.example.ogani.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ogani.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

 @Query("SELECT new com.example.ogani.repository.OrderDetailDTO(od.name, od.price, od.quantity) FROM OrderDetail od WHERE od.order.id = :orderId")
 List<OrderDetailDTO> findOrderDetailsByOrderId(@Param("orderId") Long orderId);

}
