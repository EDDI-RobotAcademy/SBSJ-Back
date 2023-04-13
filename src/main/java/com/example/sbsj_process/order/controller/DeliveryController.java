package com.example.sbsj_process.order.controller;

import com.example.sbsj_process.order.entity.Delivery;
import com.example.sbsj_process.order.service.DeliveryService;
import com.example.sbsj_process.order.service.request.DeliveryModifyRequest;
import com.example.sbsj_process.order.service.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.service.response.DeliveryListResponse;
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
    public Boolean deliveryRegister(@RequestBody DeliveryRegisterRequest deliveryRegisterRequest) {
        log.info("deliveryRegister(): "+ deliveryRegisterRequest);

        return deliveryService.register(deliveryRegisterRequest);
    }

    @GetMapping("/register/check-defaultAddress/{memberId}/{defaultAddress}")
    public Boolean defaultAddressValidation(@PathVariable("memberId") Long memberId, @PathVariable("defaultAddress") String defaultAddress) {
        log.info("defaultAddressValidation(): " + memberId + ", " + defaultAddress);

        Delivery delivery = deliveryService.defaultAddressValidation(memberId, defaultAddress);
        if(delivery == null) {
            return false;
        }
        return true;
    }

    @GetMapping("/list/{memberId}")
    public List<DeliveryListResponse> deliveryList(@PathVariable("memberId") Long memberId) {
        log.info("deliveryList(): "+ memberId);

        return deliveryService.list(memberId);
    }

    @GetMapping("/delete/{addressId}")
    public Boolean deliveryDelete(@PathVariable("addressId") Long addressId) {
        return deliveryService.delete(addressId);
    }

    @PostMapping("/modify")
    public Boolean deliveryModify(@RequestBody DeliveryModifyRequest deliveryModifyRequest) {
        log.info("deliveryModify(): "+ deliveryModifyRequest);

        return deliveryService.modify(deliveryModifyRequest);
    }

}
