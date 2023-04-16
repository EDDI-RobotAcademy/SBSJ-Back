package com.example.sbsj_process.order.entity;

import com.example.sbsj_process.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    // 오더아이템아이디 (pk)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderInfo orderInfo;
    // 오더 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    // 프로덕트 연결

    @Column(length = 16)
    private Long orderItemCount;
    // 주문상품 수량

    @Column(length = 16)
    private Long orderItemPrice;
    // 주문상품 금액



}
