package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select ci from CartItem ci join fetch ci.cart c join fetch ci.product p where c.member.memberNo=:memberNo")
    List<CartItem> findCartListByMemberNo(Long memberNo);

    @Query("select ci from CartItem ci join fetch ci.cart c join fetch ci.product p where ci.cartItemId = :cartItemId")
    CartItem findCartItemByCartItemId (Long cartItemId);


}
