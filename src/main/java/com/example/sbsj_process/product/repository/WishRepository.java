package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMember_MemberId(Long memberId);

    List<Wish> findByProduct_ProductId(Long productId);

}
