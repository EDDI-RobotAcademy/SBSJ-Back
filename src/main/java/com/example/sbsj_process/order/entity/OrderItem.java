package com.example.sbsj_process.order.entity;

import com.example.sbsj_process.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(exclude = "orderInfo")
@Getter
@Setter
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
    // 주문상품 수량 (ex. A상품 2개)

    @Column(length = 16)
    private Long orderItemPrice;
    // 주문상품 총액 (ex. A상품 1000원 + B상품 2000원 = 3000원)

    public OrderItem(OrderInfo orderInfo, Product product, Long orderItemCount, Long orderItemPrice) {
        this.orderInfo = orderInfo;
        this.product = product;
        this.orderItemCount = orderItemCount;
        this.orderItemPrice = orderItemPrice;
    }

}
