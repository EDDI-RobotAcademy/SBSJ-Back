package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByMember_MemberId(Long memberId);

    List<Delivery> findAllByMember_MemberId(Long memberId);

    void deleteByMember_MemberId(Long memberId);

    Optional<Delivery> findByAddressId(Long addressId);


    /**
     * memberId 와 defaultAddress 에 맞는 주소 가져온다.
     * @param memberId, defaultAddress
     * @return
     */
    Optional<Delivery> findByMember_MemberIdAndDefaultAddress(Long memberId, String defaultAddress);

}
