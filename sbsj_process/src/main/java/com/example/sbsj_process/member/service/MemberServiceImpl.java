package com.example.sbsj_process.member.service;

import com.example.sbsj_process.member.entity.Authentication;
import com.example.sbsj_process.member.entity.BasicAuthentication;
import com.example.sbsj_process.member.entity.Member;
import com.example.sbsj_process.member.entity.MemberProfile;
import com.example.sbsj_process.member.repository.AuthenticationRepository;
import com.example.sbsj_process.member.repository.MemberProfileRepository;
import com.example.sbsj_process.member.repository.MemberRepository;
import com.example.sbsj_process.member.service.request.MemberLoginRequest;
import com.example.sbsj_process.member.service.request.MemberRegisterRequest;
import com.example.sbsj_process.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private AuthenticationRepository authenticationRepository;
    final private RedisService redisService;
    final private MemberProfileRepository memberProfileRepository;



        
        throw new RuntimeException("가입된 사용자가 아닙니다!");
    }

    @Override
    public Boolean signUp(MemberRegisterRequest memberRegisterRequest) {
       Optional<Member> maybeMember = memberRepository.findByMemberId(memberRegisterRequest.getMemberId());
       if(maybeMember.isPresent()) {
           throw new RuntimeException("중복된 아이디 입니다.");
       } else {
           Member member = memberRegisterRequest.toMember();
           memberRepository.save(member);
           MemberProfile memberProfile = memberRegisterRequest.toMemberProfile();
           memberProfile.setMember(member);
           memberProfileRepository.save(memberProfile);
           Authentication authentication = new BasicAuthentication(member, Authentication.BASIC_AUTH, memberRegisterRequest.getPassword());
           authenticationRepository.save(authentication);
           return true;
       }
    }
}
