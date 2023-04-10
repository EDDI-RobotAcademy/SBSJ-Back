package com.example.sbsj_process.product.review.controller.form;

import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewRegisterForm {

    private String context;
    private Long starRate;


}


