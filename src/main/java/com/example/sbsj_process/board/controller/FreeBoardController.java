package com.example.sbsj_process.board.controller;

import com.example.sbsj_process.board.controller.form.freeForm.FreeBoardModifyForm;
import com.example.sbsj_process.board.controller.form.freeForm.FreeBoardRegisterForm;
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

    final private FreeBoardService freeBoardService;

    @PostMapping("/register")
    public FreeBoard freeBoardRegister(@RequestBody FreeBoardRegisterForm form) {
        log.info("freeBoardRegister(): "+ form);

        return freeBoardService.register(form.toFreeBoardRegisterRequest());
    }

    @GetMapping("/list/{startIndex}/{endIndex}")
    public List<FreeBoardListResponse> freeBoardList(@PathVariable("startIndex") int startIndex,
                                                     @PathVariable("endIndex") int endIndex) {
        log.info("freeBoardList()");

        return freeBoardService.list(startIndex, endIndex);
    }

    @GetMapping("/read/{freeBoardId}")
    public FreeBoard freeBoardRead(@PathVariable("freeBoardId") Long freeBoardId) {
        log.info("freeBoardRead(): "+ freeBoardId);

        return freeBoardService.read(freeBoardId);
    }

    @PostMapping("/modify/{freeBoardId}")
    public void freeBoardModify(@PathVariable("freeBoardId") Long freeBoardId, @RequestBody FreeBoardModifyForm form) {
        log.info("freeBoardModify(): "+ form +", freeBoardId: "+ freeBoardId);

        form.setFreeBoardId(freeBoardId);
        freeBoardService.modify(form.toFreeBoardModifyRequest());
    }

    @Transactional
    @GetMapping("/delete/{freeBoardId}")
    public void freeBoardDelete(@PathVariable("freeBoardId") Long freeBoardId) {
        log.info("freeBoardDelete(): "+ freeBoardId);

        freeBoardService.remove(freeBoardId);
    }

}
