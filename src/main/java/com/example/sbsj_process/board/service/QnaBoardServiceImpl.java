package com.example.sbsj_process.board.service;

import com.example.sbsj_process.board.entity.QnaBoard;
import com.example.sbsj_process.board.repository.QnaBoardRepository;
import com.example.sbsj_process.board.service.request.QnaBoardModifyRequest;
import com.example.sbsj_process.board.service.request.QnaBoardRegisterRequest;
import com.example.sbsj_process.board.service.response.QnaBoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaBoardServiceImpl implements QnaBoardService {

    final QnaBoardRepository qnaBoardRepository;

    @Override
    public QnaBoard register(QnaBoardRegisterRequest qnaBoardRegisterRequest) {
        QnaBoard qnaBoard = qnaBoardRegisterRequest.toQnaBoard();
        qnaBoardRepository.save(qnaBoard);

        return qnaBoard;
    }

    @Override
    public List<QnaBoardListResponse> list(int startIndex, int endIndex) {
        List<QnaBoardListResponse> qnaBoardListResponseList = qnaBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "qnaBoardId"))
                .stream()
                .map(QnaBoardListResponse::new)
                .collect(Collectors.toList());
        int size = qnaBoardListResponseList.size();
        log.info("found qnaBoard:" + size);
        if(size >= endIndex) {
            return qnaBoardListResponseList.subList(startIndex, endIndex);
        } else if(size >= startIndex) {
            return qnaBoardListResponseList.subList(startIndex, size);
        } else {
            log.info("index out of bound");
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
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
