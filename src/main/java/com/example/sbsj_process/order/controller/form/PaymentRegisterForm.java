package com.example.sbsj_process.order.controller.form;

import com.example.sbsj_process.order.service.request.PaymentRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentRegisterForm {

    private Long amount;
    private String merchant_uid;
    private OrderItemRegisterForm sendInfo;
    private String imp_uid;
    private String phoneNumber;
    private String recipientName;
    private Long addressId;
    private String road;
    private String addressDetail;
    private String zipcode;
    private String selectedDeliveryReq;

    public PaymentRegisterRequest toOrderRegisterRequest () {
        return new PaymentRegisterRequest(amount, merchant_uid, sendInfo, imp_uid, phoneNumber, recipientName, addressId, road, addressDetail, zipcode, selectedDeliveryReq);
    }
}
