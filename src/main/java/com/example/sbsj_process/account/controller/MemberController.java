package com.example.sbsj_process.account.controller;

import com.example.sbsj_process.account.controller.form.MemberLoginForm;
import com.example.sbsj_process.account.controller.form.MemberRegisterForm;
import com.example.sbsj_process.account.service.request.MemberCheckPasswordRequest;
import com.example.sbsj_process.account.service.request.MyPageModifyRequest;
import com.example.sbsj_process.account.service.response.MemberInfoResponse;
import com.example.sbsj_process.account.service.response.MemberLoginResponse;
import com.example.sbsj_process.account.service.MemberService;
import com.example.sbsj_process.security.service.RedisService;
import com.example.sbsj_process.utility.request.UserInfoRequest;
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

    @PostMapping("/sign-up/check-userId/{userId}")
    public Boolean userIdValidation(@PathVariable("userId") String userId) {
        log.info("userIdValidation(): " + userId);

        return memberService.userIdValidation(userId);
    }


    @PostMapping("/sign-up/check-email/{email}")
    public Boolean emailValidation(@PathVariable String email) {
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
    public void logout(@RequestBody UserInfoRequest userInfoRequest) {
        log.info("logout(): " + userInfoRequest);

        redisService.deleteByKey(userInfoRequest.getToken());
    }

    @Transactional
    @PostMapping("/resign")
    public void resign(@RequestBody UserInfoRequest userInfoRequest) {
        log.info("resign(): "+ userInfoRequest);

        memberService.resign(userInfoRequest.getMemberId());
        redisService.deleteByKey(userInfoRequest.getToken());
    }

    @PostMapping("/mypage/check-password")
    public Boolean passwordValidation(@RequestBody MemberCheckPasswordRequest memberRequest) {
        log.info("passwordValidation(): "+ memberRequest);

        return memberService.passwordValidation(memberRequest);
    }

    @PostMapping("/mypage/memberInfo/{memberId}")
    public MemberInfoResponse getMemberInfo(@PathVariable("memberId") Long memberId) {
        log.info("getMemberInfo(): "+ memberId);

        return memberService.getMemberInfo(memberId);
    }

    @PostMapping("/mypage/memberInfo/update/{memberId}")
    public Boolean updateMemberInfo(@PathVariable("memberId") Long memberId,
                                    @RequestBody MyPageModifyRequest myPageModifyRequest) {
        log.info("updateMemberInfo(): "+ memberId +", "+ myPageModifyRequest);

        return memberService.updateMemberInfo(memberId, myPageModifyRequest);
    }

}