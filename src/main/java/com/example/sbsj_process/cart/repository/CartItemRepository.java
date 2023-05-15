package com.example.sbsj_process.cart.repository;

import com.example.sbsj_process.cart.entity.Cart;
import com.example.sbsj_process.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select ci from CartItem ci join fetch ci.cart c join fetch ci.product p where ci.cartItemId = :cartItemId")
    CartItem findCartItemByCartItemId (Long cartItemId);

    @Query("select ci from CartItem ci join fetch ci.product p join fetch ci.cart c where c.member.memberId = :memberId")
    List<CartItem> findCartItemListWithMemberId(Long memberId);

    Optional<CartItem> findByCartItemIdAndCart_CartId(Long cartItemId, Long cartId);

    List<CartItem> findByCart(Cart cart);

    List<CartItem> findByCart_CartId(Long cartId);

    void deleteByCart_CartId(Long cartId);

}
