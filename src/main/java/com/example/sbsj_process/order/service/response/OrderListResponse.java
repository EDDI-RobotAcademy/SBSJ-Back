package com.example.sbsj_process.order.service.response;

import com.example.sbsj_process.order.entity.OrderInfo;
import com.example.sbsj_process.order.entity.OrderItem;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@ToString(exclude = "orderItemList")
public class OrderListResponse {

    // 프론트에 '주문 내역 목록' 조회를 위해 반환해줄 것들
    // 대표 상품명, 주문상품이 몇 종인지, 구매한 상품 갯수, 배송비 포함 총 결제액, 주문번호, 주문날짜

    private Long orderId;
    // pk (오더인포)

    private String orderNo;
    // 주문 번호 (오더인포)

    private Date orderDate;
    // 주문 날짜 (오더인포)

    private Long amount;
    // 총 가격 (페이먼트)

    private List<OrderItem> orderItemList;
    // 주문상품 관련 정보들


    public OrderListResponse(OrderInfo orderInfo, Long amount) {
        this.orderId = orderInfo.getOrderId();
        this.orderNo = orderInfo.getOrderNo();
        this.orderDate = orderInfo.getOrderDate();
        this.amount = amount;
        this.orderItemList = orderInfo.getOrderItemList();
    }

}
