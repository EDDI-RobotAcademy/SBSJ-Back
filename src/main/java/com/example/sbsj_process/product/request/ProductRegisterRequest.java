package com.example.sbsj_process.product.request;

import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;

@Getter
public class ProductRegisterRequest {
    private final String productName;
    private final Long price;

    public ProductRegisterRequest(String productName, Long price) {
        this.productName = productName;
        this.price = price;
    }

    public Product toProduct() {
        return new Product(productName);
    }

    public ProductInfo toProductInfo() {
        return new ProductInfo(price);
    }
}
