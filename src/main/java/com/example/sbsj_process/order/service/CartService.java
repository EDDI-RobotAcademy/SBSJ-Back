package com.example.sbsj_process.order.service;

import com.example.sbsj_process.order.request.AddCartRequest;
import com.example.sbsj_process.order.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.order.request.SelectCartItemRequest;
import com.example.sbsj_process.order.entity.CartItem;
import com.example.sbsj_process.order.response.CartItemListResponse;
import com.example.sbsj_process.utility.request.TokenRequest;
import com.example.sbsj_process.utility.request.UserInfoRequest;

import java.util.List;

public interface CartService {

    public void addCartItem(AddCartRequest addCartRequest);

    public void deleteCartItem(SelectCartItemRequest selectCartItemRequest);

    public List<CartItemListResponse> returnCartItemList(UserInfoRequest userInfoRequest);

    String changeCartItemCount (ChangeCartItemCountRequest changeCartItemCountRequest);










}
