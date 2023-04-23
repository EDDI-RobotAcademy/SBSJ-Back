package com.example.sbsj_process.product.review.repository;

import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {


     List<ProductReview> findByProduct_ProductId(Long productId);

    Optional<ProductReview> findByProductReviewId(Long productReviewId);

}