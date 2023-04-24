package com.example.sbsj_process.order.controller;

import com.example.sbsj_process.order.controller.form.PaymentRegisterForm;
import com.example.sbsj_process.order.service.OrderService;
import com.example.sbsj_process.order.service.response.OrderDetailResponse;
import com.example.sbsj_process.order.service.response.OrderListResponse;
import com.example.sbsj_process.utility.request.TokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    final OrderService service;

    @PostMapping("/register")
    public Boolean orderRegister(@RequestBody PaymentRegisterForm paymentRegisterForm){
        System.out.println("프론트에서 잘 받았나: " + paymentRegisterForm);

        return service.registerOrderInfo(paymentRegisterForm.toOrderRegisterRequest());
    }

    @PostMapping("/list")
    public List<OrderListResponse> readAllOrder(@RequestBody TokenRequest tokenRequest) {
        System.out.println("컨트롤러에서 토큰 요청: " + tokenRequest.getToken());

        return service.readOrderList(tokenRequest);
    }

//    @PostMapping("/read")
//    public OrderDetailResponse readDetailOrder(@RequestBody Long orderId) {
//        System.out.println("컨트롤러에서 오더아이디: " + orderId);
//
//        return service.readDetailOrder(orderId);
//    }

}
