package com.example.sbsj_process.order.controller;

import com.example.sbsj_process.order.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    final DeliveryService deliveryService;

    @PostMapping("/register")
    public Boolean deliveryRegister(@RequestBody DeliveryRegisterRequest deliveryRegisterRequest) {
        log.info("deliveryRegister(): "+ deliveryRegisterRequest);

        return deliveryService.register(deliveryRegisterRequest);
    }

}
