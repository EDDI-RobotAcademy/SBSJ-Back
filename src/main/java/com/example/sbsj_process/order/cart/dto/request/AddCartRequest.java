package com.example.sbsj_process.order.cart.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartRequest {

    private Long memberNo;
    private Long productId;
    private Long count;

    public AddCartRequest toAddCartRequest() {
        return new AddCartRequest(memberNo, productId, count);
    }
}
