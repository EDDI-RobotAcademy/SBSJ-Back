package com.example.sbsj_process.cart.entity;


import com.example.sbsj_process.account.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 16)
    private Long totalCount;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<CartItem> cartItemList = new ArrayList<>();

    public Cart(Long totalCount, Member member) {
        this.totalCount = totalCount;
        this.member = member;
    }

    public void setCartItemList(CartItem cartItem) {
        this.cartItemList.add(cartItem);
        cartItem.setCart(this);
    }
}
