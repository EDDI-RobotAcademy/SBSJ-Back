package com.example.sbsj_process.order.request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectCartItemRequest {

    private List<Long> selectCartItemId;


}
