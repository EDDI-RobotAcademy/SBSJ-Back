package com.example.sbsj_process.board.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FreeCommentListResponse {
    private Long freeCommentId;
    private Long FreeBoardId;
    private String comment;
    private String writer;
}