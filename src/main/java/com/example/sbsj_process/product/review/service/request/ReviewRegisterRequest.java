package com.example.sbsj_process.product.review.service.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewRegisterRequest {

    final private Long memberId;

    final private Long productId;

    final private String context;

    final private Double starRate;

    public ReviewRegisterRequest(Long memberId, Long productId, String context, Double starRate) {
        this.memberId = memberId;
        this.productId = productId;
        this.context = context;
        this.starRate = starRate;
    }

}

