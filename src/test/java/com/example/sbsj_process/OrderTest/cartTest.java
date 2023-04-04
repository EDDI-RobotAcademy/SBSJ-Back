package com.example.sbsj_process.OrderTest;

import com.example.sbsj_process.order.request.AddCartRequest;
import com.example.sbsj_process.order.request.SelectCartItemRequest;
import com.example.sbsj_process.order.entity.CartItem;
import com.example.sbsj_process.order.repository.CartItemRepository;
import com.example.sbsj_process.order.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class cartTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void 장바구니에_상품_추가_테스트 () {
        AddCartRequest addCartRequest =
                new AddCartRequest(1L, 2L, 2L);

        cartService.addCartItem(addCartRequest);

        System.out.println("장바구니 아이템 추가 테스트 완료");
    }

    @Test
    public void 장바구니에서_상품_삭제_테스트 () {
        SelectCartItemRequest selectCartItemRequest = new SelectCartItemRequest();

        cartService.deleteCartItem(selectCartItemRequest);

        System.out.println("장바구니 아이템 삭제 테스트 완료");
    }

    @Test
    public void 장바구니_아이템_조회_테스트 () {
        List<CartItem> cartItemList = cartItemRepository.findCartListByMemberNo(1L);
        System.out.println("장바구니 아이템 조회 테스트: "+ cartItemList.toString());
    }
}
