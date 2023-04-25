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