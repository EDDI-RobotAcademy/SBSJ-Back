package com.example.sbsj_process.member.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberLoginRequest {

    private final String memberId;
    private final String password;
}
