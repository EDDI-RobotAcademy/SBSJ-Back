package com.example.sbsj_process.board.service.freeBoardService;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import com.example.sbsj_process.board.entity.free.FreeComment;
import com.example.sbsj_process.board.repository.freeRepository.FreeBoardRepository;
import com.example.sbsj_process.board.repository.freeRepository.FreeCommentRepository;
import com.example.sbsj_process.board.service.request.FreeCommentRequest;
import com.example.sbsj_process.board.service.response.FreeCommentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FreeCommentServiceImpl implements FreeCommentService {

    @Autowired
    FreeCommentRepository freeCommentRepository;

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Override
    public List<FreeCommentListResponse> freeCommentList(Long freeBoardId) {
        List<FreeComment> FreeCommentList = freeCommentRepository.findAllCommentByFreeBoardId(freeBoardId);

        List<FreeCommentListResponse> FreeCommentResponseList = new ArrayList<>();

        for (FreeComment FreeComment : FreeCommentList) {
            if (FreeComment.getFreeBoard().getFreeBoardId().equals(freeBoardId)) {
                FreeCommentResponseList.add(new FreeCommentListResponse(
                        FreeComment.getFreeCommentId(), FreeComment.getFreeBoard().getFreeBoardId(),
                        FreeComment.getComment(), FreeComment.getWriter()
                ));
            }
        }
        return FreeCommentResponseList;
    }
    @Override
    public void freeCommentRegister(FreeCommentRequest commentRequest) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findById(commentRequest.getFreeBoardId());

        FreeComment freeComment = new FreeComment();
        freeComment.setFreeBoard(maybeFreeBoard.get());
        freeComment.setWriter(commentRequest.getWriter());
        freeComment.setComment(commentRequest.getComment());

        freeCommentRepository.save(freeComment);
    }

    @Override
    public FreeComment modify(Long freeCommentId, FreeCommentRequest freeCommentRequest) {
        FreeComment freeComment = freeCommentRepository.findById(freeCommentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글 존재안함." + freeCommentId));

        freeComment.update(freeCommentRequest.getComment());

        freeCommentRepository.save(freeComment);
        return freeComment;
    }

    @Override
    public void remove(Long freeCommentId) {
        freeCommentRepository.deleteById(freeCommentId);
    }
}