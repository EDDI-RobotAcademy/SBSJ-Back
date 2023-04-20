package com.example.sbsj_process.board.service;

import com.example.sbsj_process.board.entity.QnaBoard;
import com.example.sbsj_process.board.entity.QnaComment;
import com.example.sbsj_process.board.repository.QnaBoardRepository;
import com.example.sbsj_process.board.repository.QnaCommentRepository;
import com.example.sbsj_process.board.service.request.QnaCommentRequest;
import com.example.sbsj_process.board.service.response.QnaCommentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QnaCommentServiceImpl implements QnaCommentService{

    @Autowired
    QnaCommentRepository qnaCommentRepository;

    @Autowired
    QnaBoardRepository qnaBoardRepository;

    @Override
    public List<QnaCommentListResponse> qnaCommentList(Long qnaBoardId) {
        List<QnaComment> QnaCommentList = qnaCommentRepository.findAllCommentByQnaBoardId(qnaBoardId);

        List<QnaCommentListResponse> QnaCommentResponseList = new ArrayList<>();

        for (QnaComment QnaComment : QnaCommentList) {
            if (QnaComment.getQnaBoard().getQnaBoardId().equals(qnaBoardId)) {
                QnaCommentResponseList.add(new QnaCommentListResponse(
                        QnaComment.getQnaCommentId(), QnaComment.getQnaBoard().getQnaBoardId(),
                        QnaComment.getComment(), QnaComment.getWriter()
                ));
            }
        }
        return QnaCommentResponseList;
    }
    @Override
    public void qnaCommentRegister(QnaCommentRequest commentRequest) {
        Optional<QnaBoard> maybeQnaBoard = qnaBoardRepository.findById(commentRequest.getQnaBoardId());

        QnaComment qnaComment = new QnaComment();
        qnaComment.setQnaBoard(maybeQnaBoard.get());
        qnaComment.setWriter(commentRequest.getWriter());
        qnaComment.setComment(commentRequest.getComment());

        qnaCommentRepository.save(qnaComment);
    }

    @Override
    public QnaComment modify(Long qnaCommentId, QnaCommentRequest qnaCommentRequest) {
        QnaComment qnaComment = qnaCommentRepository.findById(qnaCommentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글 존재안함." + qnaCommentId));

        qnaComment.update(qnaCommentRequest.getComment());

        qnaCommentRepository.save(qnaComment);
        return qnaComment;
    }

    @Override
    public void remove(Long qnaCommentId) {
        qnaCommentRepository.deleteById(qnaCommentId);
    }
}