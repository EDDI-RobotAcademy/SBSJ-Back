package com.example.sbsj_process.account.service.response;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberInfoResponse {

    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String birthday;

    public MemberInfoResponse(Member member, MemberProfile memberProfile) {
        this.userId = member.getUserId();
        this.name = memberProfile.getName();
        this.email = memberProfile.getEmail();
        this.phoneNumber = memberProfile.getPhoneNumber();
        this.birthday = memberProfile.getBirthday();
    }
}
