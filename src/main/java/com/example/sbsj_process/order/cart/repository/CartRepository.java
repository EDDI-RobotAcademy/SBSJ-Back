package com.example.sbsj_process.order.cart.repository;

import com.example.sbsj_process.order.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMember_MemberNo(Long memberNo);

    @Query("select c from Cart c join fetch c.member m where c.member.memberNo = :memberNo")
    Cart findCartByMemberNo(Long memberNo);
}
