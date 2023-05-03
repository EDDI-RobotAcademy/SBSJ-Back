package com.example.sbsj_process.product.service.request;

import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ProductModifyRequest {
    private final String productName;
    private final Long price;

    private final String productSubName;
    private final List<String> categories;

    private final String brand;

    public ProductModifyRequest(String productName,String productSubName, Long price, List<String> categories, String brand) {
        this.productName = productName;
        this.productSubName = productSubName;
        this.price = price;
        this.categories = categories;
        this.brand = brand;
    }

    public Product toProduct() {
        return new Product(productName);
    }

    public ProductInfo toProductInfo() {
        return new ProductInfo(price, productSubName);
    }

}
