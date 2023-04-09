package com.example.sbsj_process.AccountTest;

import com.example.sbsj_process.account.repository.AuthenticationRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.account.service.request.MemberLoginRequest;
import com.example.sbsj_process.account.service.request.MemberRegisterRequest;
import com.example.sbsj_process.account.service.response.MemberLoginResponse;
import com.example.sbsj_process.account.service.MemberService;
import org.junit.jupiter.api.Test;
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

//    @Test
//    public void 멤버_로그인_성공_테스트() {
//        String signInEx = memberService.signIn(new MemberLoginRequest("ab5265", "1234"));
//        if(!signInEx.equals("틀림") && !signInEx.equals("없음")) {
//            System.out.println("로그인 성공!");
//        }
//    }
    
    @Test
    public void 멤버_로그인_실패_테스트() {
        MemberLoginResponse memberLoginResponse = memberService.signIn(new MemberLoginRequest("aaa", "bbb"));
        String token = memberLoginResponse.getToken();

        if(token.equals("틀림")) {
            System.out.println("비밀번호가 틀렸습니다.");
            return;
        } else if(token.equals("없음")) {
            System.out.println("가입되지 않은 계정입니다.");
        }
    }

    @Test
    public void 멤버_회원가입_테스트() {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest("남건호", "ab5265", "1234", "ab5265@naver.com", "19920824","01062643016");
        boolean successSignUp = memberService.signUp(memberRegisterRequest);
        if(successSignUp) {
            System.out.println("회원 가입 완료!");
        } else {
            System.out.println("회원 가입이 정상적으로 처리되지 않았습니다.");
        }
    }

}
