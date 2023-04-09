package com.example.sbsj_process.cart.service;


import com.example.sbsj_process.cart.entity.CartItem;
import com.example.sbsj_process.cart.service.request.AddCartRequest;
import com.example.sbsj_process.cart.service.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.cart.service.request.SelectCartItemRequest;
import com.example.sbsj_process.cart.service.response.CartItemListResponse;
import com.example.sbsj_process.utility.request.UserInfoRequest;

import java.util.List;

public interface CartService {

    public void addCartItem(AddCartRequest addCartRequest);

    public void deleteCartItem(SelectCartItemRequest selectCartItemRequest);

    public List<CartItem> returnCartItemList(UserInfoRequest userInfoRequest);

    String changeCartItemCount (ChangeCartItemCountRequest changeCartItemCountRequest);
}
