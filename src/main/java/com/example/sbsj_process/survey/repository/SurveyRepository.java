package com.example.sbsj_process.survey.repository;

import com.example.sbsj_process.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findByMember_MemberId(Long memberId);

}
