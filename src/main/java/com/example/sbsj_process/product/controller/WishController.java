package com.example.sbsj_process.product.controller;

import com.example.sbsj_process.product.service.WishService;
import com.example.sbsj_process.product.service.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/wish")
@Slf4j
@RestController
@RequiredArgsConstructor
public class WishController {

    final private WishService wishService;

    @GetMapping("/list/{memberId}")
    public List<WishListResponse> getWishList(@PathVariable("memberId") Long memberId) {
        log.info("getWishList: " + memberId);

        return wishService.getWishList(memberId);
    }

    @GetMapping("/set/{productId}/{memberId}")
    public Long setWish(@PathVariable("productId") Long productId, @PathVariable("memberId") Long memberId) {
        log.info("setWish: " + productId + ", " + memberId);

        return wishService.setWish(memberId, productId);
    }

}
