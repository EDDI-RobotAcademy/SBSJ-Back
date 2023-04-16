package com.example.sbsj_process.board.service;

import com.example.sbsj_process.board.entity.QnaBoard;
import com.example.sbsj_process.board.service.request.QnaBoardModifyRequest;
import com.example.sbsj_process.board.service.request.QnaBoardRegisterRequest;
import com.example.sbsj_process.board.service.response.QnaBoardListResponse;

import java.util.List;

public interface QnaBoardService {

    QnaBoard register(QnaBoardRegisterRequest qnaBoardRegisterRequest);

    List<QnaBoardListResponse> list();

    QnaBoard read(Long qnaBoardId);

    void modify(QnaBoardModifyRequest qnaBoardModifyRequest);

    void remove(Long qnaBoardId);

}
