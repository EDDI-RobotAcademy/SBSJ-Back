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
import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductInfoRepository;
import com.example.sbsj_process.product.repository.ProductRepository;

import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private final ProductInfoRepository productInfoRepository;

    @Autowired
    private final ImageRepository imageRepository;


    @Override
    public void addCartItem(AddCartRequest addCartRequest) { // @Transactional 달면 cartId가 null이 되니 달지 말 것
        Long memberId = addCartRequest.getMemberId();
        Long productId = addCartRequest.getProductId();
        Long count = addCartRequest.getCount();

        // 카트가 없으면 카트를 생성하고 카트가 있으면 아이템 추가
        Cart cart = createCartIfNoCartElseAddCartItem(memberId);

        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        Product product = null;

        if(maybeProduct.isPresent()) {
            product = maybeProduct.get();

            // 현재 멤버의 모든 장바구니 아이템 목록을 조회
            List<CartItem> cartItemList = cartItemRepository.findCartItemListWithMemberId(memberId);
            Long cartItemId = null;

            for (CartItem cartItem : cartItemList) {
                if (cartItem.getProduct().getProductId().equals(productId)) {
                    cartItemId = cartItem.getCartItemId();
                    break;
                }
            }

            // 중복 상품이 있는지 확인
            Optional<CartItem> maybeCartItem = cartItemRepository.findByCartItemIdAndCart_CartId(cartItemId, cart.getCartId());

            if(maybeCartItem.isPresent()) {
                // 중복 상품이 있다면 해당 상품의 수량을 증가
                CartItem cartItem = maybeCartItem.get();
                cartItem.setCount(cartItem.getCount() + count);
                cartItemRepository.save(cartItem);
            } else {
                // 중복 상품이 없다면 새로운 상품을 카트에 추가
                CartItem cartItem = new CartItem(product, count);
                cartItem.setCart(cart);
                cartItemRepository.save(cartItem);
            }
        }
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

        if (deleteCartItemId == null || deleteCartItemId.isEmpty()) {
            // deleteCartItemId가 null 또는 비어있는 경우 예외처리
            throw new IllegalArgumentException("deleteCartItemId cannot be null or empty");
        }

        cartItemRepository.deleteAllById(deleteCartItemId);

    }

    @Override
    public List<CartItemListResponse> returnCartItemList(UserInfoRequest userInfoRequest){
        Long memberId = userInfoRequest.getMemberId();
        List<CartItem> cartItemList = cartItemRepository.findCartItemListWithMemberId(memberId);

        List<CartItemListResponse> cartItemListResponseList = new ArrayList<>();

        for(CartItem cartItem: cartItemList) {

            Long cartId = cartItem.getCart().getCartId();
            Optional<Cart> cart = cartRepository.findById(cartId);
            Long totalCount = cart.get().getTotalCount();

            Long productId = cartItem.getProduct().getProductId();
            Optional<ProductInfo> productInfo = productInfoRepository.findByProduct_ProductId(productId);
            Long price = productInfo.get().getPrice();

            Image image = imageRepository.findByProductId(productId);
            String thumbnail = image.getThumbnail();

            CartItemListResponse cartItemListResponse = new CartItemListResponse(cartItem, totalCount, price, thumbnail);
            cartItemListResponseList.add(cartItemListResponse);
        }

        System.out.println("cartItemListResponseList: " + cartItemListResponseList);

        return cartItemListResponseList;
    }

    @Override
    public String changeCartItemCount(ChangeCartItemCountRequest changeCartItemCountRequest) {
        CartItem cartItem = cartItemRepository.findCartItemByCartItemId(changeCartItemCountRequest.getCartItemId());
        cartItem.setCount(changeCartItemCountRequest.getCount());
        cartItemRepository.save(cartItem);

        return "1";
    }

}