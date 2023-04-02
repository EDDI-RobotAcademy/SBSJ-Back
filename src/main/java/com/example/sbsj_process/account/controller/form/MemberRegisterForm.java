package com.example.sbsj_process.account.controller.form;

import com.example.sbsj_process.account.service.request.MemberRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRegisterForm {

    private String name;
    private String id;
    private String password;
    private String email;
    private String birthday;
    private String phoneNumber;

    public MemberRegisterRequest toMemberRegisterRequest () {
        return new MemberRegisterRequest(name, id, password, email, birthday, phoneNumber);
    }

}
