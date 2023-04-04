package com.example.sbsj_process.account.request;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberRegisterRequest {

    final private String name;
    final private String id;
    final private String password;
    final private String email;
    final private String birthday;
    final private String phoneNumber;

    public Member toMember () {
        return new Member(id);
    }

    public MemberProfile toMemberProfile(Member member) {
        return new MemberProfile(name, email, birthday, phoneNumber, member);
    }

}
