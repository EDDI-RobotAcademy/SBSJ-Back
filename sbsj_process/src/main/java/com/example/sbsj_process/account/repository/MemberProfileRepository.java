package com.example.sbsj_process.account.repository;

import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    void deleteByMember(Member member);

    Optional<MemberProfile> findByEmail(String email);

    Optional<MemberProfile> findByPhoneNumber(String phoneNumber);

}