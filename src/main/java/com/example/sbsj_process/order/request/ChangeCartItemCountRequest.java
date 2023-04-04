package com.example.sbsj_process.order.request;

import lombok.Data;

@Data
public class ChangeCartItemCountRequest {

    private Long cartItemId;
    private Long count;
}
