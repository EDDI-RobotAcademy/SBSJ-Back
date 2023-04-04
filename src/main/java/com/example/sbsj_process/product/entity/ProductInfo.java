package com.example.sbsj_process.product.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
public class ProductInfo {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInfoId;

    @Column(length = 16, nullable = false)
    private Long price;

    @Column(length = 16, nullable = true)
    private String wish;

    public ProductInfo(Long price) {
        this.price = price;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

}
