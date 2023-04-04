package com.example.sbsj_process.account.controller;

import com.example.sbsj_process.account.controller.form.MemberLoginForm;
import com.example.sbsj_process.account.controller.form.MemberRegisterForm;
import com.example.sbsj_process.account.response.MemberInfoResponse;
import com.example.sbsj_process.account.response.MemberLoginResponse;
import com.example.sbsj_process.account.service.MemberService;
import com.example.sbsj_process.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    final private MemberService memberService;
    final private RedisService redisService;

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("signUp(): " + form);

        return memberService.signUp(form.toMemberRegisterRequest());
    }

    @PostMapping("/sign-up/check-id/{id}")
    public Boolean idValidation(@PathVariable("id") String id) {
        log.info("idValidation(): " + id);

        return memberService.idValidation(id);
    }


    @PostMapping("/sign-up/check-email")
    public Boolean emailValidation(@RequestBody String email) {
        log.info("emailValidation(): " + email);

        return memberService.emailValidation(email);
    }

    @PostMapping("/sign-up/check-phoneNumber/{phoneNumber}")
    public Boolean phoneNumberValidation(@PathVariable("phoneNumber") String phoneNumber) {
        log.info("phoneNumberValidation(): " + phoneNumber);

        return memberService.phoneNumberValidation(phoneNumber);
    }

    @PostMapping("/sign-in")
    public MemberLoginResponse signIn(@RequestBody MemberLoginForm form) {
        log.info("signIn(): " + form);

        return memberService.signIn(form.toMemberLoginRequest());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String token) {
        token = token.substring(0, token.length() - 1);
        log.info("logout(): " + token);

        redisService.deleteByKey(token);
    }

    @Transactional
    @PostMapping("/resign")
    public void resign(@RequestBody String token) {
        token = token.substring(0, token.length() - 1);
        log.info("resign(): "+ token);

        Long memberNo = redisService.getValueByKey(token);

        memberService.delete(memberNo);
        redisService.deleteByKey(token);
    }

    @PostMapping("/mypage/check-password")
    public Boolean passwordValidation(@RequestBody MemberCheckPasswordRequest memberRequest) {
        log.info("passwordValidataion(): "+ memberRequest);

        return memberService.passwordValidation(memberRequest);
    }

    @PostMapping("/mypage/memberInfo/{memberNo}")
    public MemberInfoResponse getMemberInfo(@PathVariable("memberNo") Long memberNo) {
        log.info("getMemberInfo(): "+ memberNo);

        return memberService.getMemberInfo(memberNo);
    }

    @PostMapping("/mypage/memberInfo/update/{memberNo}")
    public Boolean updateMemberInfo(@PathVariable("memberNo") Long memberNo,
                                    @RequestBody MyPageUpdateRequest myPageUpdateRequest) {
        log.info("updateMemberInfo(): "+ memberNo +", "+ myPageUpdateRequest);

        return memberService.updateMemberInfo(memberNo, myPageUpdateRequest);
    }

}