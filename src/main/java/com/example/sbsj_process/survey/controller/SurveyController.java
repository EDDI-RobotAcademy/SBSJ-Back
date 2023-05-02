package com.example.sbsj_process.survey.controller;

import com.example.sbsj_process.survey.service.SurveyService;
import com.example.sbsj_process.survey.service.request.SurveyRegisterRequest;
import com.example.sbsj_process.survey.service.response.SurveyReadResponse;
import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

    final private SurveyService surveyService;

    @PostMapping("/register")
    public Boolean surveyRegister(@RequestBody SurveyRegisterRequest surveyRegisterRequest) {
        log.info("surveyRegister: " + surveyRegisterRequest);
        return surveyService.register(surveyRegisterRequest);
    }

}
