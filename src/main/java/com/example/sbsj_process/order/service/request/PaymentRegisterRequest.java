package com.example.sbsj_process.order.service.request;

import com.example.sbsj_process.order.controller.form.OrderItemRegisterForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class PaymentRegisterRequest {

    private final Long amount;
    private final String merchant_uid;
    private final OrderItemRegisterForm sendInfo;
    private final String imp_uid;
    private final String phoneNumber;
    private final String recipientName;
    private final Long addressId;
    private final String road;
    private final String addressDetail;
    private final String zipcode;
    private final String selectedDeliveryReq;
}
