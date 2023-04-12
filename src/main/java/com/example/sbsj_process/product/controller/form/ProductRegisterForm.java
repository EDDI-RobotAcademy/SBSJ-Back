package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.product.service.request.ProductRegisterRequest;

import java.util.List;

public class ProductRegisterForm {
    private String productName;
    private Long price;

    private List<String> categories;

    public ProductRegisterForm(String productName, Long price, List<String> categories) {
        this.productName = productName;
        this.price = price;
        this.categories = categories;
    }

    public ProductRegisterRequest toProductRegisterRequest() {
        return new ProductRegisterRequest(productName, price, categories);
    }
}
