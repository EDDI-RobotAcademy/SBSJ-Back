package com.example.sbsj_process.order.service.response;

import com.example.sbsj_process.order.entity.OrderInfo;

import com.example.sbsj_process.order.entity.OrderItem;
import com.example.sbsj_process.product.repository.ImageRepository;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class OrderListResponse {

    // 프론트에 '주문 내역 조회'를 위해 반환해줄 것들

    private final Long orderId;
    // pk

    private final String orderNo;
    // 주문 번호

    private final Date orderDate;
    // 주문 날짜

    private final Long orderTotalCount;
    // 주문 하나당 주문상품 몇 종인지

    private final String selectedDeliveryReq;
    // 배송 메시지

    private final String merchant_uid;
    // 가맹점 고유 주문번호

    private final Long amount;
    // 결제 총액 (상품 총액 + 배송비)

    private final LocalDateTime regData;
    // 결제일자

    private final String road;
    private final String addressDetail;
    private final String recipientName;
    private final String phoneNumber;
    // 배송

    private final List<OrderItemResponse> orderItemList;
    // 주문 상품 리스트

    public OrderListResponse(OrderInfo orderInfo, ImageRepository imageRepository) {
        this.orderId = orderInfo.getOrderId();
        this.orderNo = orderInfo.getOrderNo();
        this.orderDate = orderInfo.getOrderDate();
        this.orderTotalCount = orderInfo.getOrderTotalCount();
        this.selectedDeliveryReq = orderInfo.getSelectedDeliveryReq();
        this.merchant_uid = orderInfo.getPayment().getMerchant_uid();
        this.amount = orderInfo.getPayment().getAmount();
        this.regData = orderInfo.getPayment().getRegData();
        this.road = orderInfo.getDelivery().getRoad();
        this.addressDetail = orderInfo.getDelivery().getAddressDetail();
        this.recipientName = orderInfo.getDelivery().getRecipientName();
        this.phoneNumber = orderInfo.getDelivery().getPhoneNumber();

        List<OrderItemResponse> orderItemList = new ArrayList<>();
        for(OrderItem orderItem: orderInfo.getOrderItemList()) {
            orderItemList.add(new OrderItemResponse(orderItem, imageRepository));
        }
        this.orderItemList = orderItemList;
    }



}
