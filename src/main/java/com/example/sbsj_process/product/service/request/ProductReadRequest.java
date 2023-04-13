package com.example.sbsj_process.product.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductReadRequest {

    final private Long productId;
    final private Long price;
    final private Long wish;
    final private String productName;
    final private String Thumbnail;
    final private String productSubName;
    final private String detail;
}
