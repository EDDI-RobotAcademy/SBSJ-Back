package com.example.sbsj_process.order.cart.service;

import com.example.sbsj_process.order.cart.dto.request.AddCartRequest;
import com.example.sbsj_process.order.cart.dto.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.order.cart.dto.request.SelectCartItemRequest;
import com.example.sbsj_process.order.cart.dto.response.CartItemReadResponse;
import com.example.sbsj_process.order.cart.entity.CartItem;

import java.util.List;

public interface CartService {

    public void addCartItem(AddCartRequest addCartRequest);

    public void deleteCartItem(SelectCartItemRequest selectCartItemRequest);

    public List<CartItem> returnCartItemList(String userToken);

    String changeCartItemCount (ChangeCartItemCountRequest changeCartItemCountRequest);


    public CartItemReadResponse read(Long cartItemId);








}
