package com.example.sbsj_process.product.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Wish {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

}
