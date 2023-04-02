package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.product.service.request.ProductRegisterRequest;

public class ProductRegisterForm {
    private String productName;
    private Long price;

    public ProductRegisterForm(String productName, Long price) {
        this.productName = productName;
        this.price = price;
    }

    public ProductRegisterRequest toProductRegisterRequest() {
        return new ProductRegisterRequest(productName, price);
    }
}
