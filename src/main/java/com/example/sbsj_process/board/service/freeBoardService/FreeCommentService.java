package com.example.sbsj_process.board.service.freeBoardService;

import com.example.sbsj_process.board.entity.free.FreeComment;
import com.example.sbsj_process.board.service.request.FreeCommentRequest;
import com.example.sbsj_process.board.service.response.FreeCommentListResponse;

import java.util.List;

public interface FreeCommentService {
    List<FreeCommentListResponse> freeCommentList(Long freeBoardId);

    void freeCommentRegister(FreeCommentRequest freeCommentRequest);

    FreeComment modify(Long freeCommentId, FreeCommentRequest freeCommentRequest);

    void remove(Long freeCommentId);

}