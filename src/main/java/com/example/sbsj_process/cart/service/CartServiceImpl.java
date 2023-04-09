package com.example.sbsj_process.cart.service;



import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;

import com.example.sbsj_process.cart.entity.Cart;
import com.example.sbsj_process.cart.entity.CartItem;
import com.example.sbsj_process.cart.repository.CartItemRepository;
import com.example.sbsj_process.cart.repository.CartRepository;
import com.example.sbsj_process.cart.service.request.AddCartRequest;
import com.example.sbsj_process.cart.service.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.cart.service.request.SelectCartItemRequest;
import com.example.sbsj_process.cart.service.response.CartItemListResponse;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ProductRepository;

import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final ProductRepository productRepository;


    @Override
    public void addCartItem(AddCartRequest addCartRequest) {
        // @Transaction 달면 cartId가 null이 됨
        Long memberId = addCartRequest.getMemberId();
        Long productId = addCartRequest.getProductId();
        Long count = addCartRequest.getCount();

        // 카트가 없으면 카트를 생성하고 카트가 있으면 아이템 추가
        Cart cart = createCartIfNoCartElseAddCartItem(memberId);

        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        Product product = null;

        if(maybeProduct.isPresent()) {
            product = maybeProduct.get();
        }

        System.out.println("product: " + product);

        CartItem cartItem = new CartItem(product, count);
        //cartItem
        System.out.println("cartItem: " + cartItem);

        //cart.setCartItemList(cartItem);
        System.out.println("cartItem: " + cartItem);


        cartItem.setCart(cart);
        //cartRepository.save(cart);
        cartItemRepository.save(cartItem);
    }

    private Cart createCartIfNoCartElseAddCartItem(Long memberId) {
        Cart cart = null;
        Optional<Cart> maybeCart = cartRepository.findByMember_MemberId(memberId);
        if(maybeCart.isEmpty()){
            Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
            Member member = new Member();

            if(maybeMember.isPresent()) {
                member = maybeMember.get();
            }

            System.out.println(member);
            cart = new Cart(3L, member);
            cartRepository.save(cart);
        }

        // 카트에 아이템 추가할 때
        if(maybeCart.isPresent()) {
            cart = maybeCart.get();
        }

        return cart;
    }

    @Override
    public void deleteCartItem(SelectCartItemRequest selectCartItemRequest){
        List<Long> deleteCartItemId = selectCartItemRequest.getSelectCartItemId();
        //Cart cart = new Cart();

        for (int i = 0; i < deleteCartItemId.size() ; i++) {
            cartItemRepository.deleteById(deleteCartItemId.get(i));
            //cart.setTotalCount(cart.getTotalCount() - 1);
        }

    }

    @Override
    public List<CartItem> returnCartItemList(UserInfoRequest userInfoRequest){
        Long memberId = userInfoRequest.getMemberId();
        List<CartItem> myCartItemList = cartItemRepository.findCartItemListWithMemberId(memberId);

        System.out.println("cartItemList: " + myCartItemList);

        return myCartItemList;
    }

    @Override
    public String changeCartItemCount(ChangeCartItemCountRequest changeCartItemCountRequest) {
        CartItem cartItem = cartItemRepository.findCartItemByCartItemId(changeCartItemCountRequest.getCartItemId());
        cartItem.setCount(changeCartItemCountRequest.getCount());
        cartItemRepository.save(cartItem);

        return "1";
    }

//    @Override
//    public String changeCartItemCount(ChangeCartItemCountRequest changeCartItemCountRequest) {
////        CartItem cartItem = cartItemRepository.findCartItemByCartItemId(changeCartItemCountRequest.getCartItemId());
////
////        //cartItem.setCount(changeCartItemCountRequest.getCount());
////
////        cartItemRepository.save(cartItem);
//        return "1";
//    }

}