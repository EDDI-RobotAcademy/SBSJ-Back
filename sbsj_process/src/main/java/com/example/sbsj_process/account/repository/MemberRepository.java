package com.example.sbsj_process.account.repository;

import com.example.sbsj_process.account.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.authentications where m.id = :id")
    Optional<Member> findById(@Param("id") String id);

    Optional<Member> findByMemberNo(Long memberNo);

    void deleteByMemberNo(Long memberNo);

}
