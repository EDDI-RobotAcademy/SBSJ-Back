package com.example.sbsj_process.product.service;

import com.example.sbsj_process.product.service.response.WishListResponse;

import java.util.List;

public interface WishService {

    List<WishListResponse> getWishList(Long memberId);

    Long setWish(Long memberId, Long productId);

}
