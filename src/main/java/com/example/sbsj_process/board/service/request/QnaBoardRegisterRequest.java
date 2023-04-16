package com.example.sbsj_process.board.service.request;

import com.example.sbsj_process.board.entity.QnaBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class QnaBoardRegisterRequest {

    private String title;
    private String writer;
    private String content;

    public QnaBoard toQnaBoard() {
        return new QnaBoard(
                title,
                writer,
                content,
    }

}
