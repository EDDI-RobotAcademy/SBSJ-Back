package com.example.sbsj_process.account.repository;

import com.example.sbsj_process.account.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> findByMember_MemberId(Long memberId);

}
