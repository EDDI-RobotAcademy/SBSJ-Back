package com.example.sbsj_process.member.service;

import com.example.sbsj_process.member.service.request.MemberLoginRequest;


public interface MemberService {

    String signIn(MemberLoginRequest memberLoginRequest);
}
