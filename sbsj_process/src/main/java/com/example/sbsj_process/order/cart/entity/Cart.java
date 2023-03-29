package com.example.sbsj_process.order.cart.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Cart {

    @Id
    @Getter
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(length = 16)
    private Long totalCount;
    // 회원의 장바구니 등록 건수

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CartItem cartItem;







}
