package com.example.sbsj_process.product.review.service.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ReviewModifyRequest {

    private String context;

    private Double starRate;

    private Long productReviewId;

    public ReviewModifyRequest( String context, Double starRate, Long productReviewId) {

        this.context = context;
        this.starRate = starRate;
        this.productReviewId = productReviewId;
    }
}