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
import com.example.sbsj_process.order.repository.DeliveryRepository;
import com.example.sbsj_process.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        System.out.println("서비스에서 보는 delete memberNo: "+ memberId);
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);

        if(maybeMember.isEmpty()) {
//            System.out.println("서비스에서 보는 delete: "+ maybeMember.get());
            System.out.println("서비스에서 없어 그냥 없어.");
            return;
        }

        Member member = maybeMember.get();
        deliveryRepository.deleteByMember_MemberId(memberId);
        memberProfileRepository.deleteByMember(member);
        memberRepository.deleteByMemberId(memberId);
    }

    @Override
    @Transactional
    public Boolean passwordValidation(MemberCheckPasswordRequest memberRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberRequest.getMemberId());

        if(maybeMember.isEmpty()) {
            System.out.println("memberId 에 해당하는 계정이 없습니다.");
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

}