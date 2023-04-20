package com.example.sbsj_process.board.controller;

import com.example.sbsj_process.board.service.QnaCommentService;
import com.example.sbsj_process.board.service.request.QnaCommentRequest;
import com.example.sbsj_process.board.service.response.QnaCommentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/qna/read")
public class QnaCommentController {

    @Autowired
    QnaCommentService qnaCommentService;

    @GetMapping("/comments/{qnaBoardId}")
    public List<QnaCommentListResponse> qnaCommentList(@PathVariable("qnaBoardId") Long qnaBoardId) {
        log.info("qnaCommentList() 동작");

        return qnaCommentService.qnaCommentList(qnaBoardId);
    }

    @PostMapping("/register")
    public void qnaCommentRegister(@RequestBody QnaCommentRequest qnaCommentRequest) {
        log.info("qnaCommentRegister() 해당 게시물 아이디 : " + qnaCommentRequest.getQnaBoardId());
        log.info(qnaCommentRequest.getComment());
        log.info(qnaCommentRequest.getWriter());

        qnaCommentService.qnaCommentRegister(qnaCommentRequest);
    }

    @PutMapping("/{qnaCommentId}")
    public void qnaCommentModify(@PathVariable("qnaCommentId") Long qnaCommentId,
                                 @RequestBody QnaCommentRequest qnaCommentRequest) {

        qnaCommentService.modify(qnaCommentId, qnaCommentRequest);
    }

    @DeleteMapping("/{qnaCommentId}")
    public void qnaCommentRemove(@PathVariable("qnaCommentId") Long qnaCommentId) {
        log.info("qnaCommentRemove() 메소드 동작");

        qnaCommentService.remove(qnaCommentId);
    }

}