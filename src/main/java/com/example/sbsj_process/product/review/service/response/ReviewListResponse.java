package com.example.sbsj_process.product.review.service.response;

import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
@Transactional
public class ReviewListResponse {

    final private String context;

    final private Double starRate;

    final private Date createDate;

    final private Date updateDate;

    final private List<String> reviewImagePathList;

    final private Long productReviewId;

    public ReviewListResponse(ProductReview productReview) {
        this.context = productReview.getContext();
        this.starRate = productReview.getStarRate();
        this.createDate = productReview.getCreateDate();
        this.updateDate = productReview.getUpdateDate();
        this.productReviewId = productReview.getProductReviewId();

        this.reviewImagePathList = new ArrayList<>();
        List<ReviewImage> images = productReview.getReviewImageList();
        if (images != null && !images.isEmpty()) {
            for (ReviewImage image : images) {
                this.reviewImagePathList.add(image.getReviewImagePath());
            }

        }
    }
}
