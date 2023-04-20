package com.example.sbsj_process.board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QnaCommentRequest {

    final private String comment;
    final private Long qnaBoardId;
    final private String writer;

}