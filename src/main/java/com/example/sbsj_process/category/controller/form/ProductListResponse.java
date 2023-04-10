package com.example.sbsj_process.category.controller.form;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductListResponse {
    final private String title;
    final private String thumbnail;
    final private Long price;
    final private Long productId;
    final private Long wish;

    public ProductListResponse(String title, String thumbnail, Long price, Long productId, Long wish) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.productId = productId;
        this.wish = wish;
    }
}
