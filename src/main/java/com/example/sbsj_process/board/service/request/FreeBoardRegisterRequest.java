package com.example.sbsj_process.board.service.request;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FreeBoardRegisterRequest {

    private String title;
    private String writer;
    private String content;


    public FreeBoard toFreeBoard() {
        return new FreeBoard(
                title,
                writer,
                content);
    }

}
