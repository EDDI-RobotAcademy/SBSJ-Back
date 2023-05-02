package com.example.sbsj_process.survey.service.response;

import com.example.sbsj_process.survey.entity.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SurveyReadResponse {

    private String username;
    private String gender;
    private String age;
    private Double height;
    private Double weight;
    private String viscera;
    private String life;

    public SurveyReadResponse(Survey survey) {
        this.username = survey.getUsername();
        this.gender = survey.getGender();
        this.age = survey.getAge();
        this.height = survey.getHeight();
        this.weight = survey.getWeight();
        this.viscera = survey.getViscera();
        this.life = survey.getLife();
    }
}