package com.example.sbsj_process.product.review.repository;

import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {


     List<ProductReview> findByProduct_ProductId(Long productId);

    Optional<ProductReview> findByProductReviewId(Long productReviewId);

    @Query("Select pr from ProductReview pr join fetch pr.member m join fetch pr.product p where m.memberId = :memberId")
    List<ProductReview> findByMember_MemberId(Long memberId);
}