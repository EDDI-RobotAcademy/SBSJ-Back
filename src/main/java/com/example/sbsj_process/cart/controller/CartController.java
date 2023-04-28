package com.example.sbsj_process.cart.controller;



import com.example.sbsj_process.cart.entity.CartItem;
import com.example.sbsj_process.cart.service.CartService;
import com.example.sbsj_process.cart.service.request.AddCartRequest;
import com.example.sbsj_process.cart.service.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.cart.service.request.SelectCartItemRequest;
import com.example.sbsj_process.cart.service.response.CartItemListResponse;
import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
        System.out.println("아이디: " + selectCartItemRequest.getSelectCartItemId());
        cartService.deleteCartItem(selectCartItemRequest);
    }

    @PostMapping("/list")
    public List<CartItemListResponse> cartItemListResponseList(@RequestBody UserInfoRequest userInfoRequest) {
        List<CartItemListResponse> cartItemListResponseList = cartService.returnCartItemList(userInfoRequest);

        System.out.println("after returnCartItemList(): " + cartItemListResponseList);

        return cartItemListResponseList;
    }

    @PostMapping("/changeCartItemCount")
    public String changeCartItemCount (@RequestBody ChangeCartItemCountRequest changeCartItemCountRequest) {
        log.info("changeCartItemCount(): "+ changeCartItemCountRequest);
        cartService.changeCartItemCount(changeCartItemCountRequest);
        return "1";
    }
//    @PostMapping("/changeCartItemCount")
//    public String changeCartItemCount (@RequestBody Map<String, List<Long>> map) {
//        log.info("changeCartItemCount(): "+ map);
////        cartService.changeCartItemCount(changeCartItemCountRequest);
//        return "1";
//    }

}
