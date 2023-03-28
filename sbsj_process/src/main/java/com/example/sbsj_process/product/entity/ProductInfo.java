package com.example.sbsj_process.product.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ProductInfo {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInfoId;

    @Column(length = 16, nullable = false)
    private Long price;

    @Column(length = 16, nullable = false)
    private String wish;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
