package com.example.sbsj_process.order.service.response;

import com.example.sbsj_process.order.entity.OrderItem;

import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.repository.ImageRepository;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderItemResponse {

    private final Long orderItemId;
    // pk

    private final Long productId;

    private final String productName;
    // 상품명

    private final Long orderItemCount;
    // 주문상품 수량 (ex. A상품 2개)

    private final Long orderItemPrice;
    // 주문상품 총액 (ex. A상품 1000원 + B상품 2000원 = 3000원)

    private final String thumbnail;
    // 썸네일

    public OrderItemResponse(OrderItem orderItem, ImageRepository imageRepository) {
        this.orderItemId = orderItem.getOrderItemId();
        this.productId = orderItem.getProduct().getProductId();
        this.productName = orderItem.getProduct().getProductName();
        this.orderItemCount = orderItem.getOrderItemCount();
        this.orderItemPrice = orderItem.getOrderItemPrice();

        Image image = imageRepository.findByProductId(productId);
        String thumbnail = image.getThumbnail();
        this.thumbnail = thumbnail;
    }
}
