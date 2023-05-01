package com.example.sbsj_process.product.review.service.response;

import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import com.example.sbsj_process.product.review.entity.ProductReview;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberReviewListResponse {
    private String thumbnail;
    private Product product;
    private Long price;
    private Date createDate;
    private Double starRate;
    private String context;
    private Long productReviewId;

    public MemberReviewListResponse(Image image, Product product, ProductInfo productInfo, ProductReview productReview) {
        this.thumbnail = image.getThumbnail();
        this.product = product;
        this.price = productInfo.getPrice();
        this.createDate = productReview.getCreateDate();
        this.starRate = productReview.getStarRate();
        this.context = productReview.getContext();
        this.productReviewId = productReview.getProductReviewId();
    }
}
