package com.example.sbsj_process.account.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberLoginRequest {

    private final String id;
    private final String password;

}
