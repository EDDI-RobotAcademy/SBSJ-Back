package com.example.sbsj_process.product.review.repository;

import com.example.sbsj_process.product.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
//    @Query("select ri from ReviewImage ri join ri.product p where p.productId = : productId")
    List<ReviewImage> findByProductReview_ReviewId(Long reviewId);
}