package com.example.sbsj_process.account.service.request;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MyPageModifyRequest {

    private String name;
    private String email;
    private String birthday;
    private String phoneNumber;
    private String newPassword;

    public MemberProfile toMemberProfile(Member member) {
        return new MemberProfile(name, email, birthday, phoneNumber, member);
    }

}
