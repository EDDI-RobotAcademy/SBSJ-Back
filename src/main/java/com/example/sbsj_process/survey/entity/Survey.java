package com.example.sbsj_process.survey.entity;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.survey.service.request.SurveyRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Survey {

    @Id
    @Setter
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 8, nullable = false)
    private String gender;

    @Column(length = 8, nullable = false)
    private String age;

    @Column(length = 8, nullable = false)
    private Double height;

    @Column(length = 8, nullable = false)
    private Double weight;

    @Column(length = 8, nullable = false)
    private String viscera;

    @Column(length = 8, nullable = false)
    private String life;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Survey(SurveyRegisterRequest surveyRegisterRequest, Member member) {
        this.username = surveyRegisterRequest.getUsername();
        this.gender = surveyRegisterRequest.getGender();
        this.age = surveyRegisterRequest.getAge();
        this.height = surveyRegisterRequest.getHeight();
        this.weight = surveyRegisterRequest.getWeight();
        this.viscera = surveyRegisterRequest.getViscera();
        this.life = surveyRegisterRequest.getLife();
        this.member = member;
    }

}
