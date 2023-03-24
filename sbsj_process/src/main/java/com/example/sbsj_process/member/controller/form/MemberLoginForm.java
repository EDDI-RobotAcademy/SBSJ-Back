package com.example.sbsj_process.member.controller.form;

import com.example.sbsj_process.member.service.request.MemberLoginRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberLoginForm {

    private String memberId;
    private String password;

    public MemberLoginRequest toMemberLoginRequest () {
        return new MemberLoginRequest(memberId, password);
    }
}
