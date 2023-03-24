package com.example.sbsj_process.AccountMemberTest;

import com.example.sbsj_process.member.entity.Authentication;
import com.example.sbsj_process.member.entity.BasicAuthentication;
import com.example.sbsj_process.member.entity.Member;
import com.example.sbsj_process.member.repository.AuthenticationRepository;
import com.example.sbsj_process.member.repository.MemberRepository;
import com.example.sbsj_process.member.service.MemberService;
import com.example.sbsj_process.member.service.request.MemberLoginRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class memberTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Test
    public void 멤버_로그인_테스트() {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest("aaa", "bbb");
        Member member = new Member(memberLoginRequest.getMemberId());
        memberRepository.save(member);
        authenticationRepository.save(new BasicAuthentication(member, Authentication.BASIC_AUTH, memberLoginRequest.getPassword()));

        String UserToken = memberService.signIn(memberLoginRequest);
        System.out.println("UserToken: " + UserToken);

        assertThrows(RuntimeException.class, () -> memberService.signIn(new MemberLoginRequest("bbb", "aaa")));
        assertThrows(RuntimeException.class, () -> memberService.signIn(new MemberLoginRequest("aaa", "bb")));
        assertThrows(RuntimeException.class, () -> memberService.signIn(new MemberLoginRequest("aa", "bbb")));
    }

}
