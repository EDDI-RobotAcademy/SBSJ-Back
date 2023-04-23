package com.example.sbsj_process.order.controller;

import com.example.sbsj_process.order.controller.form.PaymentRegisterForm;
import com.example.sbsj_process.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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

