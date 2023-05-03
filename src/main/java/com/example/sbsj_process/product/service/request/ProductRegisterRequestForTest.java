package com.example.sbsj_process.product.service.request;

import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductRegisterRequestForTest {
    private final String productName;
    private final Long price;

    private final String productSubName;
    private final List<String> categories;

    private final String brand;

    private final String thumbnail;
    private final String detail;

    public ProductRegisterRequestForTest(String productName, String productSubName, Long price, List<String> categories, String brand, String thumbnail, String detail) {
        this.productName = productName;
        this.productSubName = productSubName;
        this.price = price;
        this.categories = categories;
        this.brand = brand;
        this.thumbnail = thumbnail;
        this.detail = detail;
    }

    public Product toProduct() {
        return new Product(productName);
    }

    public ProductInfo toProductInfo() {
        return new ProductInfo(price, productSubName);
    }

    public Image toImage() {return new Image(thumbnail, detail);}

}
