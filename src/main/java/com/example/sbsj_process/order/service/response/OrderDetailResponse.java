package com.example.sbsj_process.order.service.response;

import com.example.sbsj_process.order.entity.OrderInfo;
import com.example.sbsj_process.order.entity.OrderItem;
import com.example.sbsj_process.order.entity.Payment;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@ToString(exclude = "payment")
public class OrderDetailResponse {

    // 프론트에 '주문 내역 상세 보기'를 위해 반환해줄 것들

    private final Long orderId;
    private final String orderNo;
    private final Date orderDate;
    private final String selectedDeliveryReq;

    private final List<OrderItem> orderItemList;
    private final String thumbnail; // <- Image에서 가져옴

    private final Payment payment;

    private final String orderAddress;
    private final String orderRecipient;
    private final String orderPhoneNumber;


    public OrderDetailResponse(OrderInfo orderInfo, String thumbnail) {
        this.orderId = orderInfo.getOrderId();
        this.orderNo = orderInfo.getOrderNo();
        this.orderDate = orderInfo.getOrderDate();
        this.selectedDeliveryReq = orderInfo.getSelectedDeliveryReq();
        this.orderItemList = orderInfo.getOrderItemList();
        this.thumbnail = thumbnail;
        this.payment = orderInfo.getPayment();
        this.orderAddress = orderInfo.getOrderAddress();
        this.orderRecipient = orderInfo.getOrderRecipient();
        this.orderPhoneNumber = orderInfo.getOrderPhoneNumber();
    }
}
