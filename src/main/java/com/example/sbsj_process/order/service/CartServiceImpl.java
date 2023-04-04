package com.example.sbsj_process.order.service;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.order.request.AddCartRequest;
import com.example.sbsj_process.order.request.ChangeCartItemCountRequest;
import com.example.sbsj_process.order.request.SelectCartItemRequest;
import com.example.sbsj_process.order.entity.Cart;
import com.example.sbsj_process.order.entity.CartItem;
import com.example.sbsj_process.order.repository.CartItemRepository;
import com.example.sbsj_process.order.repository.CartRepository;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.security.service.RedisService;
import com.example.sbsj_process.utility.order.validationToken;
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

    @Autowired
    private RedisService redisService;


    @Override
    @Transactional
    public void addCartItem(AddCartRequest addCartRequest) {

        Long memberId = addCartRequest.getMemberId();
        Long productId = addCartRequest.getProductId();
        Long count = addCartRequest.getCount();

        Optional<Cart> maybeCart = cartRepository.findByMember_MemberId(memberId);


        // 카트가 생성되어 있지 않다면
        if(maybeCart.isEmpty()){
            Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
            Member member = new Member();

            if(maybeMember.isPresent()) {
                member = maybeMember.get();
            } else {
                // pass
            }
            System.out.println(member);
            Cart cart = Cart.builder() // 빌더 (@Builder) 참고
                    .member(member)
                    .totalCount(0L)
                    .build();

            cartRepository.save(cart);
        }

        // 카트에 아이템 추가할 때
        maybeCart = cartRepository.findByMember_MemberId(memberId);
        Cart cart = new Cart();

        if(maybeCart.isPresent()) {
            cart = maybeCart.get();
        } else {
            // pass
        }

        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        Product product = new Product();

        if(maybeProduct.isPresent()) {
            product = maybeProduct.get();
        } else {
            // pass
        }

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .count(count)
                .build();

        cart.setTotalCount(cart.getTotalCount() + 1);
        //cartRepository.save(cart);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(SelectCartItemRequest selectCartItemRequest){
        List<Long> deleteCartItemId = selectCartItemRequest.getSelectCartItemId();
        Cart cart = new Cart();

        for (int i = 0; i < deleteCartItemId.size() ; i++) {
            cartItemRepository.deleteById(deleteCartItemId.get(i));
            cart.setTotalCount(cart.getTotalCount() - 1);
        }

    }

    public List<CartItem> returnCartItemList(String userToken){
        String returnToken = validationToken.validationToken(userToken);
        Long memberId = redisService.getValueByKey(returnToken);

        return cartItemRepository.findCartListByMemberId(memberId);
    }

    @Override
    public String changeCartItemCount(ChangeCartItemCountRequest changeCartItemCountRequest) {
        CartItem cartItem = cartItemRepository.findCartItemByCartItemId(changeCartItemCountRequest.getCartItemId());

        cartItem.setCount(changeCartItemCountRequest.getCount());

        cartItemRepository.save(cartItem);
        return "1";
    }

}
