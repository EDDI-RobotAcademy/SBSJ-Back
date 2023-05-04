package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMember_MemberId(Long memberId);

    Optional<Wish> findByMember_MemberIdAndProduct_ProductId(Long memberId, Long productId);

    List<Wish> findByProduct_ProductId(Long productId);

    @Modifying
    @Query("delete from Wish w where w.product.productId = :productId")
    void deleteByProductId(@Param("productId") Long productId);

}
