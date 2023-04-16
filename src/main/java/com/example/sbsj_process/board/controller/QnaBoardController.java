package com.example.sbsj_process.board.controller;

import com.example.sbsj_process.board.controller.form.QnaBoardModifyForm;
import com.example.sbsj_process.board.controller.form.QnaBoardRegisterForm;
import com.example.sbsj_process.board.entity.QnaBoard;
import com.example.sbsj_process.board.service.QnaBoardService;
import com.example.sbsj_process.board.service.response.QnaBoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaBoardController {

    final private QnaBoardService qnaBoardService;

    @PostMapping("/register")
    public QnaBoard qnaBoardRegister(@RequestBody QnaBoardRegisterForm form) {
        log.info("qnaBoardRegister(): "+ form);

        return qnaBoardService.register(form.toQnaBoardRegisterRequest());
    }

    @GetMapping("/list")
    public List<QnaBoardListResponse> qnaBoardList() {
        log.info("qnaBoardList()");

        return qnaBoardService.list();
    }

    @GetMapping("/read/{qnaBoardId}")
    public QnaBoard qnaBoardRead(@PathVariable("qnaBoardId") Long qnaBoardId) {
        log.info("qnaBoardRead(): "+ qnaBoardId);

        return qnaBoardService.read(qnaBoardId);
    }

    @PostMapping("/modify/{qnaBoardId}")
    public void qnaBoardModify(@PathVariable("qnaBoardId") Long qnaBoardId, @RequestBody QnaBoardModifyForm form) {
        log.info("qnaBoardModify(): "+ form +", qnaBoardId: "+ qnaBoardId);

        form.setQnaBoardId(qnaBoardId);
        qnaBoardService.modify(form.toQnaBoardModifyRequest());
    }

    @Transactional
    @GetMapping("/delete/{qnaBoardId}")
    public void qnaBoardDelete(@PathVariable("qnaBoardId") Long qnaBoardId) {
        log.info("qnaBoardDelete(): "+ qnaBoardId);

        qnaBoardService.remove(qnaBoardId);
    }

}
