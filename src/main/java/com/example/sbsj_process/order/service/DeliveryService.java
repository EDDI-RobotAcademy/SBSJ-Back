package com.example.sbsj_process.order.service;

import com.example.sbsj_process.order.service.request.DeliveryModifyRequest;
import com.example.sbsj_process.order.service.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.service.response.DeliveryListResponse;
import com.example.sbsj_process.order.service.response.DeliveryModifyResponse;
import com.example.sbsj_process.order.service.response.DeliveryRegisterResponse;

import java.util.List;

public interface DeliveryService {

    DeliveryRegisterResponse register(DeliveryRegisterRequest deliveryRegisterRequest);

    List<DeliveryListResponse> list(Long memberId);

    Boolean delete(Long addressId);

    Boolean defaultAddressValidation(Long memberId, String defaultAddress);

    DeliveryModifyResponse modify(DeliveryModifyRequest deliveryModifyRequest);

}
