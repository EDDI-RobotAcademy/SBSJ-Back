package com.example.sbsj_process.member.repository;

import com.example.sbsj_process.member.entity.Member;
import com.example.sbsj_process.member.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    @Query("select m from MemberProfile m join fetch m.member n where n.memberId =: memberId")
    Optional<MemberProfile> findByMemberId(String memberId);
}
