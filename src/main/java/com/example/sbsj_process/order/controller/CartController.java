package com.example.sbsj_process.order.controller;

import com.example.sbsj_process.order.request.AddCartRequest;
import com.example.sbsj_process.order.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.order.request.SelectCartItemRequest;
import com.example.sbsj_process.order.entity.CartItem;
import com.example.sbsj_process.order.response.CartItemListResponse;
import com.example.sbsj_process.order.service.CartService;
import com.example.sbsj_process.utility.request.TokenRequest;
import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    final private CartService cartService;

    @PostMapping("/addCartItem")
    public void addCartItem(@RequestBody AddCartRequest addCartRequest) {
        log.info("회원 카트에 아이템 추가" + addCartRequest.toString());
        cartService.addCartItem(addCartRequest.toAddCartRequest());

    }

    @PostMapping("/deleteCartItem")
    public void deleteCartItem(@RequestBody SelectCartItemRequest selectCartItemRequest) {
        log.info("회원 카트에서 선택된 아이템 삭제");
        cartService.deleteCartItem(selectCartItemRequest);
    }


    @PostMapping("/list")
    public List<CartItemListResponse> cartItemList(@RequestBody UserInfoRequest userInfoRequest) {
        log.info("cartItemList(): " + userInfoRequest);
        List<CartItemListResponse> cartItemList = cartService.returnCartItemList(userInfoRequest);
        System.out.println("after returnCartItemList(): " + cartItemList);

        return cartItemList;
    }


    @PostMapping(path = "/changeCartItemCount")
    public String changeCartItemCount (@RequestBody ChangeCartItemCountRequest changeCartItemCountRequest) {
        cartService.changeCartItemCount(changeCartItemCountRequest);
        return "1";
    }

}
