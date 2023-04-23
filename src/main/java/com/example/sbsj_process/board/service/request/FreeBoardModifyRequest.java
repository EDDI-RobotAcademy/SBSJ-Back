package com.example.sbsj_process.board.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FreeBoardModifyRequest {

    private Long freeBoardId;
    private String title;
    private String content;

}
