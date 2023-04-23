package com.example.sbsj_process.board.controller.form.freeForm;

import com.example.sbsj_process.board.service.request.FreeBoardModifyRequest;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardModifyForm {

    private Long freeBoardId;
    private String title;
    private String content;

    public FreeBoardModifyRequest toFreeBoardModifyRequest() {
        return new FreeBoardModifyRequest(freeBoardId, title, content);
    }

}
