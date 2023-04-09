package com.example.sbsj_process.product.service.request;

import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductRegisterRequest {
    private final String productName;
    private final Long price;
    private final List<String> categorys;

    public ProductRegisterRequest(String productName, Long price, List<String> categorys) {
        this.productName = productName;
        this.price = price;
        this.categorys = categorys;
    }

    public Product toProduct() {
        return new Product(productName);
    }

    public ProductInfo toProductInfo() {
        return new ProductInfo(price);
    }

}
