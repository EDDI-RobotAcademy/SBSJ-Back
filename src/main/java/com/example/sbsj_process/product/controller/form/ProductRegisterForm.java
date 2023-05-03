package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.product.service.request.ProductModifyRequest;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;

import java.util.List;

public class ProductRegisterForm {
    private final String productName;
    private final Long price;
    private final String productSubName;
    private final List<String> categories;
    private final String brand;

    public ProductRegisterForm(String productName, String productSubName,Long price, List<String> categories, String brand) {
        this.productName = productName;
        this.price = price;
        this.categories = categories;
        this.productSubName = productSubName;
        this.brand = brand;
    }

    public ProductRegisterRequest toProductRegisterRequest() {
        return new ProductRegisterRequest(productName, productSubName, price, categories, brand);
    }

    public ProductModifyRequest toProductModifyRequest() {
        return new ProductModifyRequest(productName, productSubName, price, categories, brand);
    }
}
