package com.example.sbsj_process.board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FreeCommentRequest {

    final private String comment;
    final private Long freeBoardId;
    final private String writer;

}