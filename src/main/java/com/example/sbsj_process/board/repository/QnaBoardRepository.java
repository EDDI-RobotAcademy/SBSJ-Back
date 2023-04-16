package com.example.sbsj_process.board.repository;

import com.example.sbsj_process.board.entity.QnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, Long> {

    Optional<QnaBoard> findByQnaBoardId(Long qnaBoardId);

    void deleteByQnaBoardId(Long qnaBoardId);

}
