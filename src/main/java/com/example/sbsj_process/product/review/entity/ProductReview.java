package com.example.sbsj_process.product.review.entity;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ProductReview {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 100, nullable = false)
    private String context;

    @Column(length = 8, nullable = false)
    private Long starRate;

    @Column(length = 8)
    private Long likeRecommend;

    @Column(length = 8)
    private Long dislikeRecommend;

    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "productReview", fetch = FetchType.LAZY)
    private List<ReviewImage> reviewImageList = new ArrayList<>();


    public void setReviewImage (ReviewImage reviewImage) {
        reviewImageList.add(reviewImage);
        reviewImage.setProductReview(this);
    }

    public void setReviewImageList (List<ReviewImage> reviewImageList) {
        reviewImageList.addAll(reviewImageList);

        for (int i = 0; i < reviewImageList.size(); i++) {
            reviewImageList.get(i).setProductReview(this);
        }
    }
}