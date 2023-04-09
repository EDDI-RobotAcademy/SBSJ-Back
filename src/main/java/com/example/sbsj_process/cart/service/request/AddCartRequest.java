package com.example.sbsj_process.cart.service.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartRequest {

    private Long memberId;
    private Long productId;
    private Long count;

    public AddCartRequest toAddCartRequest() {
        return new AddCartRequest(memberId, productId, count);
    }
}
