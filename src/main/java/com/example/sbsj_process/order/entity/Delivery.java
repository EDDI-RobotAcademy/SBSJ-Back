package com.example.sbsj_process.order.entity;

import com.example.sbsj_process.account.entity.Authentication;
import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private MemberProfile memberProfile;

    public void Delivery(String city, String street, String addressDetail, String zipcode) {
        this.city = city;
        this.street = street;
        this.addressDetail = addressDetail;
        this.zipcode = zipcode;
    }

//    public Delivery of(String city, String street, String addressDetail, String zipcode) {
//        return new Delivery(city, street, addressDetail, zipcode);
//    }

    public void setMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
    }

}