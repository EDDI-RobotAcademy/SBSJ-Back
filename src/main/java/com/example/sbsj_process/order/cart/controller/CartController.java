package com.example.sbsj_process.order.cart.controller;

import com.example.sbsj_process.order.cart.dto.request.AddCartRequest;
import com.example.sbsj_process.order.cart.dto.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.order.cart.dto.request.SelectCartItemRequest;
import com.example.sbsj_process.order.cart.entity.CartItem;
import com.example.sbsj_process.order.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/list", headers = "Token")
    public List<CartItem> cartItemList(@RequestHeader("Token") String token) {
        String userToken = token;
        return cartService.returnCartItemList(token);
    }

    @PostMapping(path = "/changeCartItemCount")
    public String changeCartItemCount (@RequestBody ChangeCartItemCountRequest changeCartItemCountRequest) {
        cartService.changeCartItemCount(changeCartItemCountRequest);
        return "1";
    }



}
