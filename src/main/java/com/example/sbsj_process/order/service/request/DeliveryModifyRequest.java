package com.example.sbsj_process.order.service.request;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.order.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryModifyRequest {

    private Long addressId;
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

    public Delivery toDelivery(Member member) {
        return new Delivery(addressId, addressName, defaultAddress, addressType,
                            city, street, addressDetail, zipcode, recipientName, phoneNumber, member);
    }

}
