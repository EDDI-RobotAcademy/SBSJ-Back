package com.example.sbsj_process.product.service.response;

import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class WishListResponse {

    private Long productId;
    private String productName;
    private Long price;
    private String thumbnail;

    public WishListResponse(Product product, ProductInfo productInfo, Image image) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = productInfo.getPrice();
        this.thumbnail = image.getThumbnail();
    }

}
