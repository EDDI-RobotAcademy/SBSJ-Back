package com.example.sbsj_process.order.service;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberProfileRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.order.entity.Delivery;
import com.example.sbsj_process.order.repository.DeliveryRepository;
import com.example.sbsj_process.order.service.request.DeliveryModifyRequest;
import com.example.sbsj_process.order.service.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.service.response.DeliveryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;
    final private DeliveryRepository deliveryRepository;

    @Override
    public Boolean register(DeliveryRegisterRequest deliveryRegisterRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(deliveryRegisterRequest.getMemberId());

        if(maybeMember.isEmpty()) {
            System.out.println("memberId 에 해당하는 회원이 없습니다.");
            return false;
        }

        if(deliveryRegisterRequest.getDefaultAddress().equals("기본 배송지")) {
            defaultAddressValidation(deliveryRegisterRequest.getMemberId(), deliveryRegisterRequest.getDefaultAddress());
        }
        Delivery delivery = deliveryRegisterRequest.toDelivery(maybeMember.get());

        deliveryRepository.save(delivery);
        return true;
    }

    @Override
    public Boolean defaultAddressValidation(Long memberId,String defaultAddress) {
        Optional<Delivery> maybeDelivery = deliveryRepository.findByMember_MemberIdAndDefaultAddress(memberId, defaultAddress);
        if(maybeDelivery.isPresent()) {
            Delivery updateDelivery = maybeDelivery.get();
            updateDelivery.setDefaultAddress("");
            deliveryRepository.save(updateDelivery);
            return true;
        }
        return false;
    }

    @Override
    public List<DeliveryListResponse> list(Long memberId) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        if(maybeMember.isEmpty()) {
            System.out.println("memberNo 에 해당하는 회원이 없습니다.");
            return null;
        }

        List<Delivery> deliveryList = deliveryRepository.findAllByMember_MemberIdOrderByDefaultAddressDesc(memberId);
        List<DeliveryListResponse> deliveryListResponseList = new ArrayList<>();

        for(Delivery delivery: deliveryList) {
            DeliveryListResponse deliveryListResponse = new DeliveryListResponse(delivery);
            deliveryListResponseList.add(deliveryListResponse);
        }

        return deliveryListResponseList;
    }

    @Override
    public Boolean delete(Long addressId) {
        Optional<Delivery> maybeDelivery = deliveryRepository.findByAddressId(addressId);

        if(maybeDelivery.isEmpty()) {
            System.out.println("addressId 에 해당하는 배송지 정보가 없습니다.");
            return false;
        }

        deliveryRepository.delete(maybeDelivery.get());
        return true;
    }

    @Override
    public Boolean modify(DeliveryModifyRequest deliveryModifyRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(deliveryModifyRequest.getMemberId());

        if(maybeMember.isEmpty()) {
            System.out.println("memberId 에 해당하는 회원이 없습니다.");
            return false;
        }

        if(deliveryModifyRequest.getDefaultAddress().equals("기본 배송지")) {
            defaultAddressValidation(deliveryModifyRequest.getMemberId(), deliveryModifyRequest.getDefaultAddress());
        }
        Delivery delivery = deliveryModifyRequest.toDelivery(maybeMember.get());

        deliveryRepository.save(delivery);
        return true;
    }

}
