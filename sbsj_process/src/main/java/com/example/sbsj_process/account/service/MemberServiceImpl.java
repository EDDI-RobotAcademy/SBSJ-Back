package com.example.sbsj_process.account.service;

import com.example.sbsj_process.account.entity.Authentication;
import com.example.sbsj_process.account.entity.BasicAuthentication;
import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import com.example.sbsj_process.account.repository.AuthenticationRepository;
import com.example.sbsj_process.account.repository.MemberProfileRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.account.service.request.MemberLoginRequest;
import com.example.sbsj_process.account.service.request.MemberRegisterRequest;
import com.example.sbsj_process.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;
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
            memberRepository.deleteByMemberNo(member.getMemberNo());
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
    public Boolean idValidation(String id) {
        Optional<Member> maybeMember = memberRepository.findById(id);

        if (maybeMember.isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean emailValidation(String email) {
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByEmail(email);

        if (maybeMemberProfile.isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean phoneNumberValidation(String phoneNumber) {
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByPhoneNumber(phoneNumber);

        if (maybeMemberProfile.isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public String signIn(MemberLoginRequest memberLoginRequest) {
        Optional<Member> maybeMember = memberRepository.findById(memberLoginRequest.getId());

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();

            if(!member.isRightPassword(memberLoginRequest.getPassword())) {
                return "틀림";
            }

            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getMemberNo());

            return userToken.toString();
        }

        return "없음";
    }

    @Override
    public void delete(Long memberNo) {
        System.out.println("서비스에서 보는 delete memberNo: "+ memberNo);
        Optional<Member> maybeMember = memberRepository.findByMemberNo(memberNo);

        if(maybeMember.isEmpty()) {
//            System.out.println("서비스에서 보는 delete: "+ maybeMember.get());
            System.out.println("서비스에서 없어 그냥 없어.");
            return;
        }

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();
            memberProfileRepository.deleteByMember(member);
            memberRepository.deleteByMemberNo(memberNo);
        }

    }



}