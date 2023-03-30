package com.example.sbsj_process.product.controller.form;

import lombok.ToString;

@ToString
public class ProductDefaultResponseForm {
    private String title;
    private String thumbnail;
    private Long price;
    private Long productId;
    private Long wish;

    public ProductDefaultResponseForm(String title, String thumbnail, Long price, Long productId, Long wish) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.productId = productId;
        this.wish = wish;
    }
}
