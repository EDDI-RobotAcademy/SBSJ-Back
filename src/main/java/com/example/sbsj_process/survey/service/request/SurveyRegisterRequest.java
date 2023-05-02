package com.example.sbsj_process.survey.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SurveyRegisterRequest {

    private Long memberId;
    private String username;
    private String gender;
    private String age;
    private Double height;
    private Double weight;
    private String viscera;
    private String life;

//    private String smoking;
//    private String smokingAmount;
//    private String drinking;
//    private String drinkingAmount;
//    private String exercising;
//    private String exercisingAmount;

}