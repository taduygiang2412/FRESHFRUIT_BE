package com.example.ogani.service;


import com.example.ogani.entity.Coupon;

import java.util.List;

public interface CouponService {
    long cacuCouponValue(String couponCode , long totalAmount);
    List<Coupon> getList();
}
