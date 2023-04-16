package com.example.sbsj_process.board.controller.form;

import com.example.sbsj_process.board.service.request.QnaBoardRegisterRequest;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaBoardRegisterForm {

    private String title;
    private String writer;
    private String content;
    private String password;
    private Boolean privateCheck;

    public QnaBoardRegisterRequest toQnaBoardRegisterRequest() {
        return new QnaBoardRegisterRequest(title, writer, content, password, privateCheck);
    }

}
