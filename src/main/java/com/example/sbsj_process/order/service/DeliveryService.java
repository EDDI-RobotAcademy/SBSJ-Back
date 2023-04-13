package com.example.sbsj_process.order.service;

import com.example.sbsj_process.order.entity.Delivery;
import com.example.sbsj_process.order.service.request.DeliveryModifyRequest;
import com.example.sbsj_process.order.service.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.service.response.DeliveryListResponse;

import java.util.List;

public interface DeliveryService {

    Boolean register(DeliveryRegisterRequest deliveryRegisterRequest);

    List<DeliveryListResponse> list(Long memberId);

    Boolean delete(Long addressId);

    Delivery defaultAddressValidation(Long memberId, String defaultAddress);

    Boolean modify(DeliveryModifyRequest deliveryModifyRequest);

}
