package com.example.sbsj_process.board.repository.freeRepository;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    Optional<FreeBoard> findByFreeBoardId(Long freeBoardId);

    void deleteByFreeBoardId(Long freeBoardId);

}
