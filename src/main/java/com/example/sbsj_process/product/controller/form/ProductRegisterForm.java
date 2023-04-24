package com.example.sbsj_process.product.controller.form;

import com.example.sbsj_process.category.entity.Brand;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;

import java.util.List;

public class ProductRegisterForm {
    private String productName;
    private Long price;
    private String productSubName;
    private List<String> categories;
    private String brand;

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
}
