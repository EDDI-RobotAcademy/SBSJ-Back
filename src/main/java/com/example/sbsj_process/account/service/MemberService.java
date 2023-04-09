package com.example.sbsj_process.account.service;


import com.example.sbsj_process.account.service.request.MemberCheckPasswordRequest;
import com.example.sbsj_process.account.service.request.MemberLoginRequest;
import com.example.sbsj_process.account.service.request.MemberRegisterRequest;
import com.example.sbsj_process.account.service.request.MyPageModifyRequest;
import com.example.sbsj_process.account.service.response.MemberInfoResponse;
import com.example.sbsj_process.account.service.response.MemberLoginResponse;

public interface MemberService {

    MemberLoginResponse signIn(MemberLoginRequest toMemberLoginRequest);

    Boolean signUp(MemberRegisterRequest memberRegisterRequest);

    void resign(Long memberId);

    Boolean userIdValidation(String userId);

    Boolean emailValidation(String email);

    Boolean phoneNumberValidation(String phoneNumber);

    Boolean passwordValidation(MemberCheckPasswordRequest memberRequest);

    MemberInfoResponse getMemberInfo(Long memberId);

    Boolean updateMemberInfo(Long memberId, MyPageModifyRequest myPageModifyRequest);

}
