package com.example.sbsj_process.product.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ProductReadResponse {

     final private Long productId;
     final private Long price;
     final private Long wishCount;
     final private String productName;
     final private String thumbnail;
     final private String productSubName;
     final private String detail;
     final private Long wishId;

}
