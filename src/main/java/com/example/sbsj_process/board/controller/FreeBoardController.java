package com.example.sbsj_process.board.controller;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import com.example.sbsj_process.board.service.freeBoardService.FreeBoardService;
import com.example.sbsj_process.board.service.response.FreeBoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/free")
@RequiredArgsConstructor
public class FreeBoardController {
    @GetMapping("/list")
    public List<FreeBoardListResponse> freeBoardList() {
        log.info("freeBoardList()");

        return freeBoardService.list();
    }

    @GetMapping("/read/{freeBoardId}")
    public FreeBoard freeBoardRead(@PathVariable("freeBoardId") Long freeBoardId) {
        log.info("freeBoardRead(): "+ freeBoardId);

        return freeBoardService.read(freeBoardId);
    }

}
