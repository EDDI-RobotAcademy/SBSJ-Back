package com.example.sbsj_process.order.service;

import com.example.sbsj_process.order.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.response.DeliveryListResponse;

import java.util.List;

public interface DeliveryService {

    Boolean register(DeliveryRegisterRequest deliveryRegisterRequest);

    List<DeliveryListResponse> list(Long memberId);

    Boolean delete(Long addressId);

}
