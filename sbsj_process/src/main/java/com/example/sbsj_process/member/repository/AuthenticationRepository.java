package com.example.sbsj_process.member.repository;

import com.example.sbsj_process.member.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    @Query("select a from Authentication a join fetch a.member m where m.memberId = :memberId")
    Optional<Authentication> findByMemberId(String memberId);
}
