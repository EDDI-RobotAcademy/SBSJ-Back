package com.example.sbsj_process.order.service;

import com.example.sbsj_process.order.service.request.PaymentRegisterRequest;
import com.example.sbsj_process.order.service.response.OrderDetailResponse;
import com.example.sbsj_process.order.service.response.OrderListResponse;
import com.example.sbsj_process.utility.request.TokenRequest;

import java.util.List;

public interface OrderService {

    public Boolean registerOrderInfo(PaymentRegisterRequest paymentRegisterRequest);

    public List<OrderListResponse> readOrderList(TokenRequest tokenRequest);

    public OrderDetailResponse readDetailOrder(Long orderId);
}
