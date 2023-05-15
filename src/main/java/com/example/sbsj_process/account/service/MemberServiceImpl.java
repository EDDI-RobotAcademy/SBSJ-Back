package com.example.sbsj_process.account.service;

import com.example.sbsj_process.account.entity.Authentication;
import com.example.sbsj_process.account.entity.BasicAuthentication;
import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import com.example.sbsj_process.account.repository.AuthenticationRepository;
import com.example.sbsj_process.account.repository.MemberProfileRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.account.service.request.MemberCheckPasswordRequest;
import com.example.sbsj_process.account.service.request.MemberLoginRequest;
import com.example.sbsj_process.account.service.request.MemberRegisterRequest;
import com.example.sbsj_process.account.service.request.MyPageModifyRequest;
import com.example.sbsj_process.account.service.response.MemberInfoResponse;
import com.example.sbsj_process.account.service.response.MemberLoginResponse;
import com.example.sbsj_process.cart.entity.Cart;
import com.example.sbsj_process.cart.repository.CartItemRepository;
import com.example.sbsj_process.cart.repository.CartRepository;
import com.example.sbsj_process.order.entity.OrderInfo;
import com.example.sbsj_process.order.repository.DeliveryRepository;
import com.example.sbsj_process.order.repository.OrderItemRepository;
import com.example.sbsj_process.order.repository.OrderRepository;
import com.example.sbsj_process.product.entity.Wish;
import com.example.sbsj_process.product.repository.WishRepository;
import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.repository.ProductReviewRepository;
import com.example.sbsj_process.product.review.repository.ReviewImageRepository;
import com.example.sbsj_process.security.service.RedisService;
import com.example.sbsj_process.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;
    final private DeliveryRepository deliveryRepository;
    final private AuthenticationRepository authenticationRepository;
    final private RedisService redisService;

    final private OrderRepository orderRepository;
    final private OrderItemRepository orderItemRepository;
    final private SurveyRepository surveyRepository;
    final private CartRepository cartRepository;
    final private CartItemRepository cartItemRepository;
    final private ProductReviewRepository productReviewRepository;
    final private ReviewImageRepository reviewImageRepository;
    final private WishRepository wishRepository;

    @Override
    public Boolean signUp(MemberRegisterRequest memberRegisterRequest) {
        final Member member = memberRegisterRequest.toMember();
        final MemberProfile memberProfile = memberRegisterRequest.toMemberProfile(member);

        try{
            memberRepository.save(member);
            memberProfileRepository.save(memberProfile);
        } catch(Exception e) {
            memberRepository.deleteByMemberId(member.getMemberId());
            return false;
        }

        final BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,
                memberRegisterRequest.getPassword()
        );

        authenticationRepository.save(authentication);
        return true;
    }

    @Override
    public Boolean userIdValidation(String userId) {
        Optional<Member> maybeMember = memberRepository.findByUserId(userId);

        if (maybeMember.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean emailValidation(String email) {
//        email = email.substring(1, email.length() - 1);

        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByEmail(email);
        if (maybeMemberProfile.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean phoneNumberValidation(String phoneNumber) {
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByPhoneNumber(phoneNumber);

        if (maybeMemberProfile.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest) {
        Optional<Member> maybeMember = memberRepository.findByUserId(memberLoginRequest.getUserId());

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();
            MemberLoginResponse memberLoginResponse = new MemberLoginResponse(member.getMemberId());

            if(!member.isRightPassword(memberLoginRequest.getPassword())) {
                memberLoginResponse.setToken("incorrect");
                return memberLoginResponse;
            }

            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getMemberId());

            memberLoginResponse.setToken(userToken.toString());
            return memberLoginResponse;
        }

        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();
        memberLoginResponse.setToken("no");
        return memberLoginResponse;
    }

    @Override
    public void resign(Long memberId) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        if(maybeMember.isEmpty()) {
//            System.out.println("서비스에서 없어 그냥 없어.");
            return;
        }

        Member member = maybeMember.get();

        Optional<OrderInfo> maybeOrderInfo = orderRepository.findByMember(member);
        if(maybeOrderInfo.isPresent()) {
            OrderInfo orderInfo = maybeOrderInfo.get();
            orderItemRepository.deleteByOrderInfo_OrderId(orderInfo.getOrderId());
            orderRepository.delete(orderInfo);
        }

        surveyRepository.deleteByMember(member);
        deliveryRepository.deleteByMember_MemberId(memberId);

        Optional<Cart> maybeCart = cartRepository.findByMember_MemberId(memberId);
        if(maybeCart.isPresent()) {
            Cart cart = maybeCart.get();
            cartItemRepository.deleteByCart_CartId(cart.getCartId());
            cartRepository.delete(cart);
        }

        List<ProductReview> productReviewList = productReviewRepository.findByMember_MemberId(memberId);
        if(productReviewList.size() > 0) {
            for(ProductReview productReview : productReviewList) {
                reviewImageRepository.deleteByProductReview_ProductReviewId(productReview.getProductReviewId());
                productReviewRepository.delete(productReview);
            }
        }

        List<Wish> wishList = wishRepository.findByMember_MemberId(memberId);
        if(wishList.size() > 0) {
            wishRepository.deleteByMember_MemberId(memberId);
        }
        memberProfileRepository.deleteByMember(member);
        memberRepository.deleteByMemberId(memberId);
    }

    @Override
    @Transactional
    public Boolean passwordValidation(MemberCheckPasswordRequest memberRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberRequest.getMemberId());

        if(maybeMember.isEmpty()) {
//            System.out.println("memberId 에 해당하는 계정이 없습니다.");
            return null;
        }

        Member member = maybeMember.get();
        if(member.isRightPassword(memberRequest.getPassword())) {
            return true;
        }

        return false;
    }

    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByMember_MemberId(memberId);

        if(maybeMember.isEmpty()) {
            return null;
        }

        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(maybeMember.get(), maybeMemberProfile.get());
        return memberInfoResponse;
    }

    @Override
    public Boolean updateMemberInfo(Long memberId, MyPageModifyRequest myPageModifyRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByMember_MemberId(memberId);
        Optional<Authentication> maybeAuthentication = authenticationRepository.findByMember_MemberId(memberId);

        if(maybeMember.isEmpty()) {
            return false;
        }

        Member member = maybeMember.get();
        memberRepository.save(member);

        MemberProfile memberProfile = myPageModifyRequest.toMemberProfile(member);
        memberProfile.setMemberProfileId(maybeMemberProfile.get().getMemberProfileId());
        memberProfileRepository.save(memberProfile);

        if(!myPageModifyRequest.getNewPassword().equals("")) {
            final BasicAuthentication authentication = new BasicAuthentication(
                    member,
                    Authentication.BASIC_AUTH,
                    myPageModifyRequest.getNewPassword()
            );

            authentication.setAuthenticationId(maybeAuthentication.get().getAuthenticationId());
            authenticationRepository.save(authentication);
        }

        return true;
    }

    @Override
    @Transactional
    public String findUserIdByNameAndPhoneNumber(String name, String phoneNumber) {
        MemberProfile memberProfile = memberProfileRepository.findByNameAndPhoneNumber(name, phoneNumber);
//        System.out.println("입력된 이름: " + name);
//        System.out.println("입력된 전화번호: " + phoneNumber);

        if (memberProfile != null) {
            Member member = memberProfile.getMember();
            if (member != null) {
//                System.out.println("찾은 사용자 ID: " + member.getUserId());
                return member.getUserId();
            } else {
//                System.out.println("사용자 ID를 찾을 수 없습니다.");
                return null;
            }
        } else {
//            System.out.println("일치하는 멤버 프로필이 없습니다.");
            return null;
        }
    }

    @Override
    @Transactional
    public String findUserPwByNameAndPhoneNumber(String name, String phoneNumber) {
        MemberProfile memberProfile = memberProfileRepository.findByNameAndPhoneNumber(name, phoneNumber);

//        System.out.println("입력된 이름: " + name);
//        System.out.println("입력된 전화번호: " + phoneNumber);

        if (memberProfile == null) {
//            System.out.println("일치하는 멤버 프로필이 없습니다.");
            return null;
        }

        Member member = memberProfile.getMember();

        if (member == null) {
//            System.out.println("사용자 패스워드를 찾을 수 없습니다.");
            return null;
        }

        Optional<Authentication> maybeAuthentication = authenticationRepository.findByMember_MemberId(member.getMemberId());

        if (maybeAuthentication.isEmpty()) {
//            System.out.println("사용자 패스워드를 찾을 수 없습니다.");
            return null;
        }
        String tempPassword = generateTemporaryPassword(10);

        BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,
                tempPassword);

        authentication.setAuthenticationId(maybeAuthentication.get().getAuthenticationId());
        authenticationRepository.save(authentication);

//        System.out.println("생성된 임시 패스워드: " + tempPassword);
        return tempPassword;
    }


    public String generateTemporaryPassword(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder temporaryPassword = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            temporaryPassword.append(characters.charAt(index));
        }

        return temporaryPassword.toString();
    }
}