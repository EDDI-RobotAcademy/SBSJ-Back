package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.product.service.request.ProductModifyRequest;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;
import com.example.sbsj_process.product.service.request.ProductRegisterRequestForTest;

import java.util.List;

public class ProductRegisterFormForTest {
    private final String productName;
    private final Long price;
    private final String productSubName;
    private final List<String> categories;
    private final String brand;
    private final String thumbnail;
    private final String detail;

    public ProductRegisterFormForTest(String productName, String productSubName, Long price, List<String> categories, String brand, String thumbnail, String detail) {
        this.productName = productName;
        this.price = price;
        this.categories = categories;
        this.productSubName = productSubName;
        this.brand = brand;
        this.thumbnail = thumbnail;
        this.detail = detail;
    }

    public ProductRegisterRequestForTest toProductRegisterRequestForTest() {
        return new ProductRegisterRequestForTest(productName, productSubName, price, categories, brand, thumbnail, detail);
    }

    public ProductModifyRequest toProductModifyRequest() {
        return new ProductModifyRequest(productName, productSubName, price, categories, brand);
    }
}
