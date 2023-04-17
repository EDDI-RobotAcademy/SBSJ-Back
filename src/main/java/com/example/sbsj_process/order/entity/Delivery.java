package com.example.sbsj_process.order.entity;

import com.example.sbsj_process.account.entity.Authentication;
import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(length = 16, nullable = false)
    private String addressName;

    @Setter
    @Column(length = 8, nullable = true)
    private String defaultAddress;

    @Column(length = 8, nullable = false)
    private String addressType;

    @Column(length = 128, nullable = false)
    private String road;

    @Column(nullable = false)
    private String addressDetail;

    @Column(length = 16, nullable = false)
    private String zipcode;

    @Column(length = 8, nullable = false)
    private String recipientName;

    @Column(length = 16, nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Delivery(String addressName, String defaultAddress, String addressType,
                    String road, String addressDetail, String zipcode,
                    String recipientName, String phoneNumber, Member member) {
        this.addressName = addressName;
        this.defaultAddress = defaultAddress;
        this.addressType = addressType;
        this.road = road;
        this.addressDetail = addressDetail;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.recipientName = recipientName;
        this.member = member;
    }

}