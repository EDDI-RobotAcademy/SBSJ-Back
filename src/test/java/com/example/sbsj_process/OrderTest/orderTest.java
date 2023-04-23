package com.example.sbsj_process.OrderTest;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.order.controller.form.OrderItemRegisterForm;
import com.example.sbsj_process.order.entity.*;
import com.example.sbsj_process.order.repository.DeliveryRepository;
import com.example.sbsj_process.order.repository.OrderRepository;
import com.example.sbsj_process.order.repository.PaymentRepository;
import com.example.sbsj_process.order.service.OrderService;
import com.example.sbsj_process.order.service.request.PaymentRegisterRequest;
import com.example.sbsj_process.order.service.response.OrderDetailResponse;
import com.example.sbsj_process.order.service.response.OrderListResponse;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.security.service.RedisService;
import com.example.sbsj_process.utility.request.TokenRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class orderTest {

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
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Test
    public void 주문_등록_테스트 () {
        List<Long> test1 = new ArrayList<>();
        test1.add(1L);
        OrderItemRegisterForm sendInfo = new OrderItemRegisterForm(test1, test1, test1, test1);

        PaymentRegisterRequest paymentRegisterRequest = new PaymentRegisterRequest(
                10000L,"pay_id", sendInfo, "impid0000", "010-1111-1111", "홍길동",
                "서울시 강남구 테헤란로 남도빌딩", "3층", "10202", "빨리 와주세요");

        try {
            //결제 정보 저장
            Payment payment = registerPayment(paymentRegisterRequest);

            OrderInfo orderInfo = new OrderInfo();
            OrderItemRegisterForm orderItemRegisterForm = paymentRegisterRequest.getSendInfo();

            // 주문번호 생성
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Random random = new Random();
            int orderNumber = random.nextInt(100000);
            String fullOrderNumber = "SBSJ" + year + orderNumber;
            List<String> existOrderNumberList = orderRepository.findFullOrderNumberByOrderNumber();

            // 주문번호 중복 확인
            while(existOrderNumberList.contains(fullOrderNumber)) {
                orderNumber = random.nextInt(100000);
                fullOrderNumber = "SBSJ" + year + orderNumber;
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

            String defaultAddress = "기본 배송지";
            Optional<Delivery> defaultDilivery = deliveryRepository.findByMember_MemberIdAndDefaultAddress(memberId, defaultAddress);
            if (defaultDilivery.isPresent()) {
                orderInfo.setDelivery(defaultDilivery.get());
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

                //orderItemRepository.save(orderItem);
            }

            orderInfo.setOrderItemList(orderItemList);

            orderRepository.save(orderInfo);

        } catch (Exception e) {
            System.out.println("오류 발생" + e);
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

