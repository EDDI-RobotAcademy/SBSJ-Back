package com.example.sbsj_process.order.cart.entity;

import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class CartItem {

    @Id
    @Getter
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @OneToMany(mappedBy = "cartItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductInfo productInfo;

    @Column(length = 16)
    private Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
