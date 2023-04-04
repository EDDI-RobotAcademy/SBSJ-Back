package com.example.sbsj_process.account.service;


import com.example.sbsj_process.account.request.MemberCheckPasswordRequest;
import com.example.sbsj_process.account.request.MemberLoginRequest;
import com.example.sbsj_process.account.request.MemberRegisterRequest;
import com.example.sbsj_process.account.request.MyPageUpdateRequest;
import com.example.sbsj_process.account.response.MemberInfoResponse;
import com.example.sbsj_process.account.response.MemberLoginResponse;

public interface MemberService {

    MemberLoginResponse signIn(MemberLoginRequest toMemberLoginRequest);

    Boolean signUp(MemberRegisterRequest memberRegisterRequest);

    void delete(Long memberNo);

    Boolean idValidation(String id);

    Boolean emailValidation(String email);

    Boolean phoneNumberValidation(String phoneNumber);

}
