package com.example.sbsj_process.order.service;


import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.order.controller.form.OrderItemRegisterForm;
import com.example.sbsj_process.order.entity.*;
import com.example.sbsj_process.order.repository.DeliveryRepository;
import com.example.sbsj_process.order.repository.OrderRepository;
import com.example.sbsj_process.order.repository.PaymentRepository;
import com.example.sbsj_process.order.service.request.PaymentRegisterRequest;
import com.example.sbsj_process.order.service.response.OrderDetailResponse;
import com.example.sbsj_process.order.service.response.OrderListResponse;
import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.security.service.RedisService;
import com.example.sbsj_process.utility.request.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    RedisService redisService;


    @Override
    public Boolean registerOrderInfo(PaymentRegisterRequest paymentRegisterRequest) {
        try {
            //결제 정보 저장
            Payment payment = registerPayment(paymentRegisterRequest);

            OrderInfo orderInfo = new OrderInfo();
            OrderItemRegisterForm orderItemRegisterForm = paymentRegisterRequest.getSendInfo();

            // 주문번호 생성
            Random random = new Random();
            int orderNumber = random.nextInt(1000000000);
            String fullOrderNumber = "SBSJ" + orderNumber;
            List<String> existOrderNumberList = orderRepository.findFullOrderNumberByOrderNumber();

            // 주문번호 중복 확인
            while(existOrderNumberList.contains(fullOrderNumber)) {
                orderNumber = random.nextInt(1000000000);
                fullOrderNumber = "SBSJ" + orderNumber;
            }

            // 주문 정보 저장
            orderInfo.setOrderNo(fullOrderNumber);
            orderInfo.setOrderDate(new Date());
            orderInfo.setPayment(payment);
            orderInfo.setSelectedDeliveryReq(paymentRegisterRequest.getSelectedDeliveryReq());
//            orderInfo.setOrderStatus(OrderStatus.PAYMENT_COMPLETE);

            Long memberId = orderItemRegisterForm.getMemberId().get(0);
            Optional<Member> maybeMember = memberRepository.findById(memberId);
            if (maybeMember.isPresent()) {
                orderInfo.setMember(maybeMember.get());
            }

            Long addressId = paymentRegisterRequest.getAddressId();
            Optional<Delivery> selectDelivery = deliveryRepository.findByAddressId(addressId);
            if (selectDelivery.isPresent()) {
                orderInfo.setDelivery(selectDelivery.get());
            }

            // 주문 상품 정보 저장
            List<OrderItem> orderItemList = new ArrayList<>();
            List<Long> productIdList = orderItemRegisterForm.getProductId();
            List<Long> orderCountList = orderItemRegisterForm.getOrderCount();
            List<Long> orderPriceList = orderItemRegisterForm.getOrderPrice();

            for (int i = 0; i < productIdList.size(); i++) {
                Long productId = productIdList.get(i);
                Long orderCount = orderCountList.get(i);
                Long orderPrice = orderPriceList.get(i);

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemCount(orderCount);
                orderItem.setOrderItemPrice(orderPrice);
                orderItem.setProduct(product);
                orderItem.setOrderInfo(orderInfo);

                orderItemList.add(orderItem);
            }

            orderInfo.setOrderItemList(orderItemList);

            orderRepository.save(orderInfo);

            return true;

        } catch (Exception e) {
            System.out.println("오류 발생" + e);
            return false;
        }
    }

    public Payment registerPayment(PaymentRegisterRequest paymentRegisterRequest) {

        // 결제 정보 생성
        Payment payment = new Payment(
                paymentRegisterRequest.getMerchant_uid(),
                paymentRegisterRequest.getImp_uid(),
                paymentRegisterRequest.getAmount()
        );

        // 결제 정보 저장
        Payment savedPayment = paymentRepository.save(payment);

        return savedPayment;
    }

    @Override
    @Transactional
    public List<OrderListResponse> readOrderList(TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        System.out.println("토큰 잘나오나: " + token);
        Long memberId = redisService.getValueByKey(token);
        System.out.println("멤버아이디 잘나오나: " + memberId);

        List<OrderInfo> orderList = orderRepository.findAllByMember_MemberIdOrderByOrderDateDesc(memberId);
        List<OrderListResponse> orderListResponseList = new ArrayList<>();
        Set<Long> orderIdSet = new HashSet<>(); // 중복 체크를 위한 Set

        for(OrderInfo orderInfo: orderList) {
            OrderListResponse orderListResponse = new OrderListResponse(orderInfo, imageRepository);
            if (orderIdSet.add(orderListResponse.getOrderId())) { // Set에 이미 존재하는 경우는 추가하지 않음
                orderListResponseList.add(orderListResponse);
            }
        }

        System.out.println("리스폰스리스트 잘나오나: " + orderListResponseList);

        return orderListResponseList;
    }

    @Override
    @Transactional
    public OrderDetailResponse readDetailOrder(Long orderId) {

        // 주문 정보 조회
        OrderInfo orderInfo = orderRepository.findOrderInfoWithOrderItemListAndProductByOrderId(orderId);

        // 썸네일 이미지 조회
        Long productId = orderInfo.getOrderItemList().get(0).getProduct().getProductId();
        Image image = imageRepository.findByProductId(productId);
        String thumbnail = image.getThumbnail();

        // OrderDetailResponse 객체 생성
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse(orderInfo, thumbnail);

        return orderDetailResponse;
    }

}

