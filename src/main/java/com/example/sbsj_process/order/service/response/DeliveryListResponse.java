package com.example.sbsj_process.order.service.response;

import com.example.sbsj_process.order.entity.Delivery;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeliveryListResponse {

    private Long addressId;
    private String addressName;
    private String defaultAddress;
    private String addressType;
    private String city;
    private String street;
    private String addressDetail;
    private String zipcode;
    private String recipientName;
    private String phoneNumber;

    public DeliveryListResponse(Delivery delivery) {
        this.addressId = delivery.getAddressId();
        this.addressName = delivery.getAddressName();
        this.defaultAddress = delivery.getDefaultAddress();
        this.addressType = delivery.getAddressType();
        this.city = delivery.getCity();
        this.street = delivery.getStreet();
        this.addressDetail = delivery.getAddressDetail();
        this.zipcode = delivery.getZipcode();
        this.recipientName = delivery.getRecipientName();
        this.phoneNumber = delivery.getPhoneNumber();
    }

}
