package com.example.sbsj_process.product.service.request;

import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductRegisterRequest {
    private final String productName;
    private final Long price;

    private final String productSubName;
    private final List<String> categories;

    public ProductRegisterRequest(String productName,String productSubName, Long price, List<String> categories) {
        this.productName = productName;
        this.productSubName = productSubName;
        this.price = price;
        this.categories = categories;
    }

    public Product toProduct() {
        return new Product(productName);
    }

    public ProductInfo toProductInfo() {
        return new ProductInfo(price, productSubName);
    }

}
