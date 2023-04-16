package com.example.sbsj_process.board.controller;
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

    @GetMapping("/list")
    public List<QnaBoardListResponse> qnaBoardList() {
        log.info("qnaBoardList()");

        return qnaBoardService.list();
    }
}
