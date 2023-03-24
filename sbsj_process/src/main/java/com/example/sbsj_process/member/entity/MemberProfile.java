package com.example.sbsj_process.member.entity;

import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
public class MemberProfile {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String memberName;
    @Getter
    private String email;
    @Getter
    private String phoneNumber;
    @Getter
    private String birthday;

    public MemberProfile(String memberName, String email, String phoneNumber, String birthday) {
        this.memberName = memberName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}
