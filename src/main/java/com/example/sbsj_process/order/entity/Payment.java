package com.example.sbsj_process.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    // pk

    @Column(length = 16, nullable = false)
    private String merchant_uid;
    // 가맹점 고유 주문번호

    @Column(length = 16, nullable = false)
    private String imp_uid;
    // 아임포트 결제모듈에서 결제건별로 고유하게 채번하는 ID

    @Column(length = 16, nullable = false)
    private Long amount;
    // 결제 총액 (상품 총액 + 배송비)

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd (HH시 mm분)", timezone = "Asia/Seoul")
    private LocalDateTime regData = LocalDateTime.now();
    // 결제일자

    public Payment(String merchant_uid, String imp_uid, Long amount) {
        this.merchant_uid = merchant_uid;
        this.imp_uid = imp_uid;
        this.amount = amount;
    }

}
