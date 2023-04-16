package com.example.sbsj_process.board.controller.form;

import com.example.sbsj_process.board.service.request.QnaBoardModifyRequest;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaBoardModifyForm {

    private Long qnaBoardId;
    private String title;
    private String content;

    public QnaBoardModifyRequest toQnaBoardModifyRequest() {
        return new QnaBoardModifyRequest(qnaBoardId, title, content);
    }

}
