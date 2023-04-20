package com.example.sbsj_process.board.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QnaCommentListResponse {
    private Long qnaCommentId;
    private Long QnaBoardId;
    private String comment;
    private String writer;
}