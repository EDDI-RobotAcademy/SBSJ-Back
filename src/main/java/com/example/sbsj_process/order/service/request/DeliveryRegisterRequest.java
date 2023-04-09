package com.example.sbsj_process.order.service.request;

import com.example.sbsj_process.order.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRegisterRequest {

    private Long memberId;
    private String addressName;
    private String defaultAddress;
    private String addressType;
    private String city;
    private String street;
    private String addressDetail;
    private String zipcode;
    private String recipientName;
    private String phoneNumber;

    public Delivery toDelivery() {
        return new Delivery(addressName, defaultAddress, addressType, city, street, addressDetail, zipcode, recipientName, phoneNumber);
    }

}
