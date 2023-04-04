package com.example.sbsj_process.order.cart.dto.request;

import lombok.Data;

@Data
public class ChangeCartItemCountRequest {

    private Long cartItemId;
    private Long count;
}
