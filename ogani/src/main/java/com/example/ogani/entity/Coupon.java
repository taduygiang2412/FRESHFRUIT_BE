package com.example.ogani.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="code" ,unique = true)
    private String code;
//    @Column(name="active",nullable = false)
//    private Boolean active ;

}
