package com.example.sbsj_process.product.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class ProductInfo {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInfoId;

    @Column(length = 16, nullable = false)
    private Long price;

    @Column(length = 16, nullable = false)
    private Long wish;
    private String productSubName;

    public ProductInfo(Long price, String productSubName) {
        this.wish = 0L;
        this.price = price;
        this.productSubName = productSubName;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

}
