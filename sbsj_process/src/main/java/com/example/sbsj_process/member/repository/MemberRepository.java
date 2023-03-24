package com.example.sbsj_process.member.repository;

import com.example.sbsj_process.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // FETCH TYPE LAZY에 의해 proxy 참조가 안되서 join fetch로 강제 참조시킴
    @Query("select m from Member m join fetch m.authentications where m.memberId = :memberId")
    Optional<Member> findByMemberId(String memberId);
}
