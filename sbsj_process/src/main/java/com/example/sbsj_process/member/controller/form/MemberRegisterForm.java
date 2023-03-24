package com.example.sbsj_process.member.controller.form;

import com.example.sbsj_process.member.service.request.MemberRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberRegisterForm {
    private String memberName;
    private String memberId;
    private String password;
    private String email;
    private String birthday;
    private String phoneNumber;

    public MemberRegisterRequest toMemberRegisterRequest() {
        return new MemberRegisterRequest(memberName, memberId, password, email, birthday, phoneNumber);
    }
}
