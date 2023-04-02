package com.example.sbsj_process.account.entity;

import com.example.sbsj_process.order.entity.Delivery;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfile {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 32, unique = true, nullable = false)
    private String email;

    @Column(length = 16, unique = true, nullable = false)
    private String phoneNumber;

//    @Column(length = 8, nullable = true)
//    private String gender;

    @Column(length = 16, nullable = false)
    private String birthday;

//    @Column(length = 8, nullable = true)
//    private String grade;

//    @OneToMany(mappedBy = "memberProfile", fetch = FetchType.LAZY)
//    private List<Delivery> deliveryList;

    public MemberProfile(String name, String email, String phoneNumber, String birthday, Member member) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.member = member;
    }

//    public MemberProfile(Member member, String name, String email, String phoneNumber, String birthday, List<Delivery> deliveryList) {
//        this.member = member;
//        this.name = name;
//        this.email = email;
//        this.birthday = birthday;
//        this.phoneNumber = phoneNumber;
//        this.deliveryList = deliveryList;
//
//        for(Delivery delivery: deliveryList) {
//            delivery.setMemberProfile(this);
//        }
//    }

    public void setMember(Member member) {
        this.member = member;
    }

//    private MemberProfile(List<Delivery> deliveryList) {
//        this.deliveryList = deliveryList;
//    }

//    public MemberProfile of (String name, String email, String birthday, String phoneNumber) {
//
//        return new MemberProfile();
//    }

}
