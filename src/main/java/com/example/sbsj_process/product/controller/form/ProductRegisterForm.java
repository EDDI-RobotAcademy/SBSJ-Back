package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.product.service.request.ProductRegisterRequest;

import java.util.List;

public class ProductRegisterForm {
    private String productName;
    private Long price;
    private String productSubName;

    private List<String> categories;

    public ProductRegisterForm(String productName, String productSubName,Long price, List<String> categories) {
        this.productName = productName;
        this.price = price;
        this.categories = categories;
        this.productSubName = productSubName;
    }

    public ProductRegisterRequest toProductRegisterRequest() {
        return new ProductRegisterRequest(productName, productSubName, price, categories);
    }
}
