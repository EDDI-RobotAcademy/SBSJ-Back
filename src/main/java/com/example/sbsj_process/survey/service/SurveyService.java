package com.example.sbsj_process.survey.service;

import com.example.sbsj_process.survey.service.request.SurveyRegisterRequest;
import com.example.sbsj_process.survey.service.response.SurveyReadResponse;
import com.example.sbsj_process.utility.request.UserInfoRequest;

public interface SurveyService {

    Boolean register(SurveyRegisterRequest surveyRegisterRequest);

    SurveyReadResponse read(UserInfoRequest userInfoRequest);

}