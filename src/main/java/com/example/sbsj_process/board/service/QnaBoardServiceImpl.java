package com.example.sbsj_process.board.service;

import com.example.sbsj_process.board.entity.QnaBoard;
import com.example.sbsj_process.board.repository.QnaBoardRepository;
import com.example.sbsj_process.board.service.request.QnaBoardModifyRequest;
import com.example.sbsj_process.board.service.request.QnaBoardRegisterRequest;
import com.example.sbsj_process.board.service.response.QnaBoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaBoardServiceImpl implements QnaBoardService {

    final QnaBoardRepository qnaBoardRepository;

    @Override
    public QnaBoard register(QnaBoardRegisterRequest qnaBoardRegisterRequest) {
        QnaBoard qnaBoard = qnaBoardRegisterRequest.toQnaBoard();
        qnaBoardRepository.save(qnaBoard);

        return qnaBoard;
    }

    @Override
    public List<QnaBoardListResponse> list() {
        List<QnaBoard> qnaBoardList = qnaBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "qnaBoardId"));
        List<QnaBoardListResponse> qnaBoardListResponseList = new ArrayList<>();

        for(QnaBoard qnaBoard: qnaBoardList) {
            QnaBoardListResponse qnaBoardListResponse = new QnaBoardListResponse(qnaBoard);
            qnaBoardListResponseList.add(qnaBoardListResponse);
        }

        return qnaBoardListResponseList;
    }

    @Override
    public QnaBoard read(Long qnaBoardId) {
        Optional<QnaBoard> maybeQnaBoard = qnaBoardRepository.findByQnaBoardId(qnaBoardId);

        if(maybeQnaBoard.isEmpty()) {
            System.out.println("qnaBoardId 에 해당하는 게시물이 존재하지 않습니다.");
            return null;
        }

        return maybeQnaBoard.get();
    }

    @Override
    public void modify(QnaBoardModifyRequest qnaBoardModifyRequest) {
        Optional<QnaBoard> maybeQnaBoard = qnaBoardRepository.findByQnaBoardId(qnaBoardModifyRequest.getQnaBoardId());

        if(maybeQnaBoard.isEmpty()) {
            System.out.println("qnaBoardId 에 해당하는 게시물이 존재하지 않습니다.");
            return;
        }

        QnaBoard qnaBoard = maybeQnaBoard.get();
        qnaBoard.setTitle(qnaBoardModifyRequest.getTitle());
        qnaBoard.setContent(qnaBoardModifyRequest.getContent());

        qnaBoardRepository.save(qnaBoard);
    }

    @Override
    public void remove(Long qnaBoardId) {
        Optional<QnaBoard> maybeQnaBoard = qnaBoardRepository.findByQnaBoardId(qnaBoardId);

        if(maybeQnaBoard.isEmpty()) {
            System.out.println("qnaBoardId 에 해당하는 게시물이 존재하지 않습니다.");
            return;
        }

        System.out.println("서비스에서 보는 삭제: "+ maybeQnaBoard.get());

        qnaBoardRepository.deleteByQnaBoardId(qnaBoardId);
    }

}
