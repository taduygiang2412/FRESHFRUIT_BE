package com.example.ogani.controller;

import com.example.ogani.entity.Coupon;
import com.example.ogani.entity.Order;
import com.example.ogani.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@CrossOrigin(origins = "*",maxAge = 3600)
@RequiredArgsConstructor
public class CouponCotroller {
    private final CouponService couponService;

    @GetMapping("/calculate")
    public ResponseEntity<Long> cacuCouponValue(
            @RequestParam("couponCode") String couponCode,
            @RequestParam("totalAmount") long totalAmount){
        long finalAmount = couponService.cacuCouponValue(couponCode , totalAmount);
        return ResponseEntity.ok(finalAmount);
    }

    @GetMapping("/getList")
    @Operation(summary="Lấy ra danh sách discount")
    public ResponseEntity<List<Coupon>> getList(){
        List<Coupon> list = couponService.getList();

        return ResponseEntity.ok(list);
    }


}
