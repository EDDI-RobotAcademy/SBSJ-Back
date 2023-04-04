package com.example.sbsj_process.order.cart.dto.request;

import com.example.sbsj_process.order.cart.entity.Cart;
import com.example.sbsj_process.order.cart.entity.CartItem;
import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectCartItemRequest {

    private List<Long> selectCartItemId;


}
