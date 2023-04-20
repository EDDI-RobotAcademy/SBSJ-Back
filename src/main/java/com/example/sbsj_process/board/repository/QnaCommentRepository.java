package com.example.sbsj_process.board.repository;

import com.example.sbsj_process.board.entity.QnaBoard;
import com.example.sbsj_process.board.entity.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment,Long> {

    @Query("select qc from QnaComment qc join fetch qc.qnaBoard qb where qb.qnaBoardId = :qnaBoardId")
    List<QnaComment> findAllCommentByQnaBoardId(Long qnaBoardId);

    void deleteAllByQnaBoard(QnaBoard qnaBoard);
}