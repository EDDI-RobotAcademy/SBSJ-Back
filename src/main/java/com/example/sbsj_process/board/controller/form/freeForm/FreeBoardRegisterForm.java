package com.example.sbsj_process.board.controller.form.freeForm;

import com.example.sbsj_process.board.service.request.FreeBoardRegisterRequest;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardRegisterForm {

    private String title;
    private String writer;
    private String content;



    public FreeBoardRegisterRequest toFreeBoardRegisterRequest() {
        return new FreeBoardRegisterRequest(title, writer, content);
    }

}
