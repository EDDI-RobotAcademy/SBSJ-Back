package com.example.sbsj_process.cart.service.response;


import com.example.sbsj_process.cart.entity.Cart;
import com.example.sbsj_process.cart.entity.CartItem;
import com.example.sbsj_process.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CartItemListResponse {


    private Long cartItemId;

    private Product product;

    private Long count;
    // 상품 개수 (ex. A상품 2개)

//    private Long totalCount; // <- cart에서 가져옴
    // 카트에 등록된 총량

    private Long price; // <- productInfo에서 가져옴

    private String thumbnail; // <- Image에서 가져옴

    public CartItemListResponse(CartItem cartItem, Long price, String thumbnail) {
        this.cartItemId = cartItem.getCartItemId();
        this.product = cartItem.getProduct();
        this.count = cartItem.getCount();
        this.price = price;
        this.thumbnail = thumbnail;
    }

}
