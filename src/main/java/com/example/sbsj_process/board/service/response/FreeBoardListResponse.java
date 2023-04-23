package com.example.sbsj_process.board.service.response;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class FreeBoardListResponse {

    private Long freeBoardId;
    private String title;
    private String writer;
    private String content;
    private Date regDate;
    private Date upDate;

    public FreeBoardListResponse(@JsonProperty("freeBoard") FreeBoard freeBoard) {
        this.freeBoardId = freeBoard.getFreeBoardId();
        this.title = freeBoard.getTitle();
        this.writer = freeBoard.getWriter();
        this.content = freeBoard.getContent();
        this.regDate = freeBoard.getRegDate();
        this.upDate = freeBoard.getUpDate();


    }

}
