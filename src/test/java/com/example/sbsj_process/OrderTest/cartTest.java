package com.example.sbsj_process.OrderTest;


import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.cart.entity.Cart;
import com.example.sbsj_process.cart.entity.CartItem;
import com.example.sbsj_process.cart.repository.CartItemRepository;
import com.example.sbsj_process.cart.repository.CartRepository;
import com.example.sbsj_process.cart.service.CartService;
import com.example.sbsj_process.cart.service.request.AddCartRequest;
import com.example.sbsj_process.cart.service.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.cart.service.request.SelectCartItemRequest;
import com.example.sbsj_process.cart.service.response.CartItemListResponse;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.utility.request.UserInfoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class cartTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void 장바구니에_상품_추가_테스트 () {
        AddCartRequest addCartRequest =
                new AddCartRequest(2L, 2L, 1L);

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
        cartRepository.save(cart);
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

    @Test
    public void 장바구니에서_상품_삭제_테스트 () {
        List<Long> selectCartItemId = Arrays.asList(11L); // 원하는 아이템 ID 넣기
        SelectCartItemRequest selectCartItemRequest = new SelectCartItemRequest(selectCartItemId);

        cartService.deleteCartItem(selectCartItemRequest);

        System.out.println("장바구니 아이템 삭제 테스트 완료");
    }

    @Test
    public void 장바구니_아이템_조회_테스트 () {
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setMemberId(1L);

        List<CartItemListResponse> cartItemListResponseList = cartService.returnCartItemList(userInfoRequest);

        System.out.println("카트아이템리스트 리스폰스 조회: " + cartItemListResponseList);
    }
}
