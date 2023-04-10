package com.example.sbsj_process.product.review.service.request;


import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter

@RequiredArgsConstructor
public class ReviewRegisterRequest {


    final private String context;

    final private Long starRate;

}