package com.example.sbsj_process.product.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Image {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(length = 16, nullable = false)
    private String thumbnail;

    @Column(length = 16, nullable = false)
    private String detail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_info_id")
    private Member member;

}
