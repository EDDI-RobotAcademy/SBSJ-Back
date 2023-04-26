package com.example.sbsj_process.order.controller;

import com.example.sbsj_process.order.service.DeliveryService;
import com.example.sbsj_process.order.service.request.DeliveryModifyRequest;
import com.example.sbsj_process.order.service.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.service.response.DeliveryListResponse;
import com.example.sbsj_process.order.service.response.DeliveryModifyResponse;
import com.example.sbsj_process.order.service.response.DeliveryRegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    final DeliveryService deliveryService;

    @PostMapping("/register")
    public DeliveryRegisterResponse deliveryRegister(@RequestBody DeliveryRegisterRequest deliveryRegisterRequest) {
        log.info("deliveryRegister(): "+ deliveryRegisterRequest);

        return deliveryService.register(deliveryRegisterRequest);
    }

    @GetMapping("/register/check-defaultAddress/{memberId}/{defaultAddress}")
    public Boolean defaultAddressValidation(@PathVariable("memberId") Long memberId, @PathVariable("defaultAddress") String defaultAddress) {
        log.info("defaultAddressValidation(): " + memberId + ", " + defaultAddress);

        return deliveryService.defaultAddressValidation(memberId, defaultAddress);
    }

    @GetMapping("/list/{memberId}")
    public List<DeliveryListResponse> deliveryList(@PathVariable("memberId") Long memberId) {
        log.info("deliveryList(): "+ memberId);

        return deliveryService.list(memberId);
    }

    @GetMapping("/delete/{addressId}")
    public Boolean deliveryDelete(@PathVariable("addressId") Long addressId) {
        log.info("deliveryDelete(): "+ addressId);

        return deliveryService.delete(addressId);
    }

    @PostMapping("/modify")
    public DeliveryModifyResponse deliveryModify(@RequestBody DeliveryModifyRequest deliveryModifyRequest) {
        log.info("deliveryModify(): "+ deliveryModifyRequest);

        return deliveryService.modify(deliveryModifyRequest);
    }

}
