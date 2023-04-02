package com.example.sbsj_process.account.controller.form;

import com.example.sbsj_process.account.service.request.MemberLoginRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberLoginForm {

    private String id;
    private String password;

    public MemberLoginRequest toMemberLoginRequest () {
        return new MemberLoginRequest(id, password);
    }
}
