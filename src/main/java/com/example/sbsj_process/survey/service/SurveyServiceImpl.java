package com.example.sbsj_process.survey.service;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.survey.entity.Survey;
import com.example.sbsj_process.survey.repository.SurveyRepository;
import com.example.sbsj_process.survey.service.request.SurveyRegisterRequest;
import com.example.sbsj_process.survey.service.response.SurveyReadResponse;
import com.example.sbsj_process.utility.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    final private MemberRepository memberRepository;
    final private SurveyRepository surveyRepository;

    @Override
    public Boolean register(SurveyRegisterRequest surveyRegisterRequest) {
        Long memberId = surveyRegisterRequest.getMemberId();
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        if(maybeMember.isEmpty()) {
//            System.out.println("멤버가 존재하지 않습니다.");
            return false;
        }

        Optional<Survey> maybeSurvey = surveyRepository.findByMember_MemberId(memberId);
        if(maybeSurvey.isPresent()) {
            Survey updateSurvey = new Survey(surveyRegisterRequest, maybeMember.get());
            updateSurvey.setSurveyId(maybeSurvey.get().getSurveyId());
            surveyRepository.save(updateSurvey);
            return true;
        }

        Survey survey = new Survey(surveyRegisterRequest, maybeMember.get());
        surveyRepository.save(survey);
        return true;
    }

    @Override
    public SurveyReadResponse read(UserInfoRequest userInfoRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(userInfoRequest.getMemberId());
        if(maybeMember.isEmpty()) {
//            System.out.println("멤버가 존재하지 않습니다.");
            return null;
        }

        Optional<Survey> maybeSurvey = surveyRepository.findByMember_MemberId(userInfoRequest.getMemberId());
        if(maybeSurvey.isEmpty()) {
            return null;
        }

        SurveyReadResponse surveyReadResponse = new SurveyReadResponse(maybeSurvey.get());
        return surveyReadResponse;
    }

}