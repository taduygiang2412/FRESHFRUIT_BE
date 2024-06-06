package com.example.ogani.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.core.SpringVersion;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupon_conditions")
public class CouponCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "attribute")
    private String attribute;

    @Column(name = "operator")
    private String operator;

    @Column(name = "value")
    private String value;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    @JsonBackReference
    private Coupon coupon;

}
