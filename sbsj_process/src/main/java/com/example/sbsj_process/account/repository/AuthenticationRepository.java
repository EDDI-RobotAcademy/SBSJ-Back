package com.example.sbsj_process.account.repository;

import com.example.sbsj_process.account.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {


}
