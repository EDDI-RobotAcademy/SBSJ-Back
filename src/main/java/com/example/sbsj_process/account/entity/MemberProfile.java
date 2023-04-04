package com.example.sbsj_process.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfile {

    @Id
    @Setter
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberProfileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 32, unique = true, nullable = false)
    private String email;

    @Column(length = 16, unique = true, nullable = false)
    private String phoneNumber;

    @Column(length = 16, nullable = false)
    private String birthday;

//    @Column(length = 8, nullable = true)
//    private String grade;

    public MemberProfile(String name, String email, String birthday, String phoneNumber, Member member) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
