package com.example.sbsj_process.cart.service.request;

import lombok.Data;

import java.util.List;

@Data
public class ChangeCartItemCountRequest {

    private List<Long> cartItemIdList;
    private List<Long> countList;
}
