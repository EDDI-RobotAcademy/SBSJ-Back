package com.example.sbsj_process.board.repository.freeRepository;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import com.example.sbsj_process.board.entity.free.FreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeCommentRepository extends JpaRepository<FreeComment,Long> {

    @Query("select qc from FreeComment qc join fetch qc.freeBoard qb where qb.freeBoardId = :freeBoardId")
    List<FreeComment> findAllCommentByFreeBoardId(Long freeBoardId);

    void deleteAllByFreeBoard(FreeBoard freeBoard);
}