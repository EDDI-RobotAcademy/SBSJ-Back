package com.example.sbsj_process.board.service.response;

import com.example.sbsj_process.board.entity.QnaBoard;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class QnaBoardListResponse {

    private Long qnaBoardId;
    private String title;
    private String writer;
    private String password;
    private Boolean privateCheck;
    private String content;
    private Date regDate;
    private Date upDate;

    public QnaBoardListResponse(@JsonProperty("qnaBoard") QnaBoard qnaBoard) {
        this.qnaBoardId = qnaBoard.getQnaBoardId();
        this.title = qnaBoard.getTitle();
        this.writer = qnaBoard.getWriter();
        this.password = qnaBoard.getPassword();
        this.privateCheck = qnaBoard.getPrivateCheck();
        this.content = qnaBoard.getContent();
        this.regDate = qnaBoard.getRegDate();
        this.upDate = qnaBoard.getUpDate();


    }

}
