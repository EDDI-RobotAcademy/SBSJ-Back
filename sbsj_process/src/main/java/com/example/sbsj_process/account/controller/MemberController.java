package com.example.sbsj_process.account.controller;

import com.example.sbsj_process.account.controller.form.MemberLoginForm;
import com.example.sbsj_process.account.controller.form.MemberRegisterForm;
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
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
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

    @PostMapping("/sign-up/check-email/{email}")
    public Boolean emailValidation(@PathVariable("email") String email) {
        log.info("emailValidation(): " + email);

        return memberService.emailValidation(email);
    }

    @PostMapping("/sign-up/check-phoneNumber/{phoneNumber}")
    public Boolean phoneNumberValidation(@PathVariable("phoneNumber") String phoneNumber) {
        log.info("phoneNumberValidation(): " + phoneNumber);

        return memberService.phoneNumberValidation(phoneNumber);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody MemberLoginForm form) {
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

}