package com.example.sbsj_process.board.service;

import com.example.sbsj_process.board.entity.QnaComment;
import com.example.sbsj_process.board.service.request.QnaCommentRequest;
import com.example.sbsj_process.board.service.response.QnaCommentListResponse;

import java.util.List;

public interface QnaCommentService {

    void qnaCommentRegister(QnaCommentRequest qnaCommentRequest);

    QnaComment modify(Long qnaCommentId, QnaCommentRequest qnaCommentRequest);

}