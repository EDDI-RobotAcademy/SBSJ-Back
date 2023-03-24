package com.example.sbsj_process.member.service;

import com.example.sbsj_process.member.entity.Authentication;
import com.example.sbsj_process.member.entity.BasicAuthentication;
import com.example.sbsj_process.member.entity.Member;
import com.example.sbsj_process.member.repository.AuthenticationRepository;
import com.example.sbsj_process.member.repository.MemberRepository;
import com.example.sbsj_process.member.service.request.MemberLoginRequest;
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



    @Override
    public String signIn(MemberLoginRequest memberLoginRequest) {
        Optional<Member> maybeMember =
                memberRepository.findByMemberId(memberLoginRequest.getMemberId());

        if (maybeMember.isPresent()) {
            Member member = maybeMember.get();

            if (!member.isRightPassword(memberLoginRequest.getPassword())) {
                throw new RuntimeException("이메일 및 비밀번호 입력이 잘못되었습니다!");
            }

            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getId());
            
            return userToken.toString();
        }
        
        throw new RuntimeException("가입된 사용자가 아닙니다!");
    }
}
