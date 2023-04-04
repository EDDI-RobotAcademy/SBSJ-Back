package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByMember_MemberId(Long memberId);

    List<Delivery> findAllByMember_MemberId(Long memberId);

}
