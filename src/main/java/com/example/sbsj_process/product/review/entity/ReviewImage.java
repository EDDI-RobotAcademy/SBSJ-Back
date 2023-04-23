package com.example.sbsj_process.product.review.entity;


import com.example.sbsj_process.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(length = 32)
    private String reviewImagePath;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_review_id")
    private ProductReview productReview;

    public ReviewImage(String reviewImagePath) {
        this.reviewImagePath = reviewImagePath;

    }

}