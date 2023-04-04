package com.example.sbsj_process.order.entity;

import com.example.sbsj_process.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @Getter
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // productInfo에서 product로 수정

    @Column(length = 16)
    private Long count;
    // 상품 개수 (ex. A상품 2개)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    // 이미 담겨있는 물건 또 담을 경우 수량 증가
    public void addCount(Long count) {
        this.count += count;
    }
}
