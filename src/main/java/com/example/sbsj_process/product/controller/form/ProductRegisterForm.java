package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.product.service.request.ProductRegisterRequest;

import java.util.List;

public class ProductRegisterForm {
    private String productName;
    private Long price;

    private List<String> categorys;

    public ProductRegisterForm(String productName, Long price, List<String> categorys) {
        this.productName = productName;
        this.price = price;
        this.categorys = categorys;
    }

    public ProductRegisterRequest toProductRegisterRequest() {
        return new ProductRegisterRequest(productName, price, categorys);
    }
}
