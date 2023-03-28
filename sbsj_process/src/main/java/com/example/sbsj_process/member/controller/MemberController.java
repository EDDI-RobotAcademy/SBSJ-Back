package com.example.sbsj_process.member.controller;


import com.example.sbsj_process.member.controller.form.MemberLoginForm;
import com.example.sbsj_process.member.controller.form.MemberRegisterForm;
import com.example.sbsj_process.member.service.MemberService;
import com.example.sbsj_process.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    final private MemberService memberService;
    final private RedisService redisService;


    @PostMapping("/sign-in")
    public String signIn(@RequestBody MemberLoginForm form) {
        log.info("signIn(): " + form);

        return memberService.signIn(form.toMemberLoginRequest());
    }

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("sign-up: " + form);

        return memberService.signUp(form.toMemberRegisterRequest());
    }
}
