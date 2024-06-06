package com.example.ogani.repository;

import com.example.ogani.entity.CouponCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponConditionRepository extends JpaRepository<CouponCondition,Long> {
    List<CouponCondition> findByCouponId(Long couponId);
}
