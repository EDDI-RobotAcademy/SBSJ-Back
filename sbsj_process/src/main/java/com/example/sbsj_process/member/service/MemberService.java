package com.example.sbsj_process.member.service;

import com.example.sbsj_process.member.controller.form.MemberRegisterForm;
import com.example.sbsj_process.member.service.request.MemberLoginRequest;
import com.example.sbsj_process.member.service.request.MemberRegisterRequest;


public interface MemberService {

    String signIn(MemberLoginRequest memberLoginRequest);
    Boolean signUp(MemberRegisterRequest memberRegisterRequest);
}
