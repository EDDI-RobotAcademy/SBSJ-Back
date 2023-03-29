package com.example.sbsj_process.AccountMemberTest;

import com.example.sbsj_process.account.entity.Authentication;
import com.example.sbsj_process.account.entity.BasicAuthentication;
import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.AuthenticationRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.account.service.MemberService;
import com.example.sbsj_process.account.service.request.MemberLoginRequest;
import com.example.sbsj_process.account.service.request.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
        Member member = new Member(memberLoginRequest.getId());
        memberRepository.save(member);
        authenticationRepository.save(new BasicAuthentication(member, Authentication.BASIC_AUTH, memberLoginRequest.getPassword()));

        String UserToken = memberService.signIn(memberLoginRequest);
        System.out.println("UserToken: " + UserToken);

        assertThrows(RuntimeException.class, () -> memberService.signIn(new MemberLoginRequest("bbb", "aaa")));
        assertThrows(RuntimeException.class, () -> memberService.signIn(new MemberLoginRequest("aaa", "bb")));
        assertThrows(RuntimeException.class, () -> memberService.signIn(new MemberLoginRequest("aa", "bbb")));
    }

    @Test
    public void 멤버_회원가입_테스트() {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest("남건호", "ab5265", "1234", "ab5265@naver.com", "19920824","01062643016");
        assertTrue(memberService.signUp(memberRegisterRequest));
        assertThrows(RuntimeException.class, () -> memberService.signUp(memberRegisterRequest));
    }
}
