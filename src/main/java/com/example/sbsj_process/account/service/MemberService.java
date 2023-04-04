package com.example.sbsj_process.account.service;

import com.example.sbsj_process.account.request.MemberLoginRequest;
import com.example.sbsj_process.account.request.MemberRegisterRequest;

public interface MemberService {

    String signIn(MemberLoginRequest toMemberLoginRequest);

    Boolean signUp(MemberRegisterRequest memberRegisterRequest);

    void delete(Long memberNo);

    Boolean idValidation(String id);

    Boolean emailValidation(String email);

    Boolean phoneNumberValidation(String phoneNumber);

}
