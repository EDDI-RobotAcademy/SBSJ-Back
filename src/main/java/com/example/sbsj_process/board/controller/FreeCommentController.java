package com.example.sbsj_process.board.controller;

import com.example.sbsj_process.board.service.freeBoardService.FreeCommentService;
import com.example.sbsj_process.board.service.request.FreeCommentRequest;
import com.example.sbsj_process.board.service.response.FreeCommentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/free/read")
public class FreeCommentController {

    @Autowired
    FreeCommentService freeCommentService;

    @GetMapping("/comments/{freeBoardId}")
    public List<FreeCommentListResponse> freeCommentList(@PathVariable("freeBoardId") Long freeBoardId) {
        log.info("freeCommentList() 동작");

        return freeCommentService.freeCommentList(freeBoardId);
    }

    @PostMapping("/register")
    public void freeCommentRegister(@RequestBody FreeCommentRequest freeCommentRequest) {
        log.info("freeCommentRegister() 해당 게시물 아이디 : " + freeCommentRequest.getFreeBoardId());
        log.info(freeCommentRequest.getComment());
        log.info(freeCommentRequest.getWriter());

        freeCommentService.freeCommentRegister(freeCommentRequest);
    }

    @PutMapping("/{freeCommentId}")
    public void freeCommentModify(@PathVariable("freeCommentId") Long freeCommentId,
                                 @RequestBody FreeCommentRequest freeCommentRequest) {

        freeCommentService.modify(freeCommentId, freeCommentRequest);
    }

    @DeleteMapping("/{freeCommentId}")
    public void freeCommentRemove(@PathVariable("freeCommentId") Long freeCommentId) {
        log.info("freeCommentRemove() 메소드 동작");

        freeCommentService.remove(freeCommentId);
    }

}