package com.example.sbsj_process.product.review.repository;

import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    /*Product의 productId를 입력 받아 해당 상품의 Review 엔티티를 조회*/
//     @Query("select r from Review r join fetch r.product  p where p.productId= :productId")
     List<ProductReview> findByProduct_ProductId(Long productId);
    //memberId를 입력 받아 해당 회원이 작성한 Review 엔티티를 조회
    //@Query("select r from Review r join fetch r.orderInfo.()) join fetch r.product join fetch r.member m where m.id= :memberId")
    //List<Review> findByMemberId(Long memberId);
    Optional<ProductReview> findByReviewId(Long reviewId);

}