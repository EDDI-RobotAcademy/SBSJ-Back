package com.example.sbsj_process.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberCheckPasswordRequest {

    private Long memberId;

    private String password;

}
