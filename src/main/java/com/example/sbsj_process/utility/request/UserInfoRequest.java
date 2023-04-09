package com.example.sbsj_process.utility.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInfoRequest {

    private Long memberId;
    private String token;

}
