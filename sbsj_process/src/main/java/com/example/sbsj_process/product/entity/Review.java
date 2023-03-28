package com.example.sbsj_process.product.entity;

import com.example.sbsj_process.account.entity.Member;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Review {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member member;

    @Column(length = 16, nullable = false)
    private String context;

    @Column(length = 8, nullable = false)
    private Long starRate;

    @Column(length = 8)
    private Long recommend;

    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;

}
