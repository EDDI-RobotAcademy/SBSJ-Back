package com.example.sbsj_process.product.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class ProductModifyFormResponse {

    final private Long productId;
    private final String productName;
    private final Long price;
    private final String productSubName;
    private final List<String> categories;
    private final String brand;
    final private String thumbnail;
    final private String detail;
}
