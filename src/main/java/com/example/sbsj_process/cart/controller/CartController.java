package com.example.sbsj_process.cart.controller;



import com.example.sbsj_process.cart.entity.CartItem;
import com.example.sbsj_process.cart.service.CartService;
import com.example.sbsj_process.cart.service.request.AddCartRequest;
import com.example.sbsj_process.cart.service.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.cart.service.request.SelectCartItemRequest;
import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CartItem> cartItemList(@RequestBody UserInfoRequest userInfoRequest) {
        //System.out.println("유저인포: " + userInfoRequest);

        List<CartItem> cartItemList = cartService.returnCartItemList(userInfoRequest);

        System.out.println("after returnCartItemList(): " + cartItemList);

        return cartItemList;
    }


    @PostMapping("/changeCartItemCount")
    public String changeCartItemCount (@RequestBody ChangeCartItemCountRequest changeCartItemCountRequest) {
        cartService.changeCartItemCount(changeCartItemCountRequest);
        return "1";
    }

}
