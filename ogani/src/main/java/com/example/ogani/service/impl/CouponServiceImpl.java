package com.example.ogani.service.impl;

import com.example.ogani.entity.Coupon;
import com.example.ogani.entity.CouponCondition;
import com.example.ogani.exception.NotFoundException;
import com.example.ogani.repository.CouponConditionRepository;
import com.example.ogani.repository.CouponRepository;
import com.example.ogani.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponConditionRepository couponConditionRepository;
    @Override
    public long cacuCouponValue(String couponCode, long totalAmount) {
        Coupon coupon = couponRepository.findByCode(couponCode).orElseThrow(() -> new NotFoundException("Not Found Coupon"));
        long discount = cacuDiscount(coupon,totalAmount);
        long finalAmount = totalAmount - discount;
        return  finalAmount;
    }

    @Override
    public List<Coupon> getList() {
        return couponRepository.findAll(Sort.by("id").descending());
    }

    private Long cacuDiscount(Coupon coupon , long totalAmount){
        List<CouponCondition> conditions = couponConditionRepository.findByCouponId(coupon.getId());
        long discount = 0;
        long updateTotalAmount = totalAmount;
        for (CouponCondition condition : conditions){
            String attribute = condition.getAttribute();
            String operator = condition.getOperator();
            String value = condition.getValue();

            double percentDiscount = Double.valueOf(String.valueOf(condition.getDiscountAmount()));

            if(attribute.equals("minimum_amount")){
                if(operator.equals(">") && updateTotalAmount > Double.parseDouble(value)) {
                    discount += updateTotalAmount * percentDiscount/100;
                }
            }
//            else if(attribute.equals("appliable_date")){
//                LocalDate appliableDate = LocalDate.parse(value);
//                LocalDate currenDate = LocalDate.now();
//                if(operator.equalsIgnoreCase("BETWEEN") && currenDate.isEqual(appliableDate)){
//                    discount += updateTotalAmount * percentDiscount/100;
//                }

            updateTotalAmount = updateTotalAmount - discount;
        }
        return discount;

    };

}
