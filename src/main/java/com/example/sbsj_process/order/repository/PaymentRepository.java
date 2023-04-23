package com.example.sbsj_process.order.repository;

import com.example.sbsj_process.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByPaymentId(Long paymentId);
}
