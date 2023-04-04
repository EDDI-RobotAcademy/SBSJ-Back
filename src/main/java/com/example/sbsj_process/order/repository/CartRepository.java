package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMember_MemberId(Long memberId);

    @Query("select c from Cart c join fetch c.member m where c.member.memberId = :memberId")
    Cart findCartByMemberId(Long memberId);
}
