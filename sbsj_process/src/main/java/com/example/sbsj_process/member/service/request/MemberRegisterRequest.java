package com.example.sbsj_process.member.service.request;

import com.example.sbsj_process.member.entity.Member;
import com.example.sbsj_process.member.entity.MemberProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberRegisterRequest {
    private final String memberName;
    private final String memberId;
    private final String password;
    private final String email;
    private final String birthday;
    private final String phoneNumber;

    public Member toMember() {
        return new Member(memberId);
    }

    public MemberProfile toMemberProfile() {
        return new MemberProfile(memberName, email, birthday, phoneNumber);
    }
}
