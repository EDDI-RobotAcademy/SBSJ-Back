package com.example.sbsj_process.order.service;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberProfileRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.order.entity.Delivery;
import com.example.sbsj_process.order.repository.DeliveryRepository;
import com.example.sbsj_process.order.request.DeliveryRegisterRequest;
import com.example.sbsj_process.order.response.DeliveryListResponse;
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
        Optional<Member> maybeMember = memberRepository.findByMemberNo(deliveryRegisterRequest.getMemberNo());

        if(maybeMember.isEmpty()) {
            System.out.println("memberNo 에 해당하는 회원이 없습니다.");
            return false;
        }

        Delivery delivery = deliveryRegisterRequest.toDelivery();
        delivery.setMember(maybeMember.get());

        deliveryRepository.save(delivery);
        return true;
    }

    @Override
    public List<DeliveryListResponse> list(Long memberNo) {
        Optional<Member> maybeMember = memberRepository.findByMemberNo(memberNo);
        if(maybeMember.isEmpty()) {
            System.out.println("memberNo 에 해당하는 회원이 없습니다.");
            return null;
        }

        List<Delivery> deliveryList = deliveryRepository.findAllByMember_MemberNo(memberNo);
        List<DeliveryListResponse> deliveryListResponseList = new ArrayList<>();

        for(Delivery delivery: deliveryList) {
            DeliveryListResponse deliveryListResponse = new DeliveryListResponse(delivery);
            deliveryListResponseList.add(deliveryListResponse);
        }
        return deliveryListResponseList;
    }

}
