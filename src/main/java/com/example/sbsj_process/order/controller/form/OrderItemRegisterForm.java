package com.example.sbsj_process.order.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItemRegisterForm {
    private List<Long> productId;

    private List<Long> memberId;

    private List<Long> orderCount;

    private List<Long> orderPrice;

    public OrderItemRegisterForm(List<Long> productId, List<Long> memberId, List<Long> orderCount, List<Long> orderPrice){
        this.productId = productId;
        this.memberId = memberId;
        this.orderCount = orderCount;
        this.orderPrice = orderPrice;
    }
}
