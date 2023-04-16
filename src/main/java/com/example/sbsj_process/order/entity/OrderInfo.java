package com.example.sbsj_process.order.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    // 오더 아이디 (pk)

    @Column(length = 16, nullable = false)
    private String orderNo;
    // 주문번호

    @CreationTimestamp
    private Date orderDate;
    // 주문날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    // 주문자

    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList = new ArrayList<>();
    // 오더아이템 연결


}
