package com.example.sbsj_process.category.service.response;

import com.example.sbsj_process.product.entity.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ProductListResponse {
    final private String title;
    final private String thumbnail;
    final private Long price;
    final private Long productId;
    final private Long wishCount;
    final private List<String> productOptions;
    final private String brand;

    public ProductListResponse(String title, String thumbnail, Long price, Long productId, Long wishCount, List<String> productOptions, String brand) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
        this.productId = productId;
        this.wishCount = wishCount;
        this.productOptions = productOptions;
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductListResponse that = (ProductListResponse) o;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return title.hashCode() + productId.intValue();
    }
}
