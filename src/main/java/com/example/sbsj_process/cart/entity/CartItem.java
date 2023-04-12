package com.example.sbsj_process.cart.entity;


import com.example.sbsj_process.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@ToString(exclude = "cart")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // productInfo에서 product로 수정

    @Column(length = 16)
    private Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(Product product, Long count) {
        this.product = product;
        this.count = count;
    }

    public void setCart (Cart cart) {
        this.cart = cart;
        //cart.setCartItemList(this);
    }
}
