package com.example.sbsj_process.board.service.freeBoardService;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import com.example.sbsj_process.board.repository.freeRepository.FreeBoardRepository;
import com.example.sbsj_process.board.service.request.FreeBoardRegisterRequest;
import com.example.sbsj_process.board.service.response.FreeBoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {

    final FreeBoardRepository freeBoardRepository;

    @Override
    public FreeBoard register(FreeBoardRegisterRequest freeBoardRegisterRequest) {
        FreeBoard freeBoard = freeBoardRegisterRequest.toFreeBoard();
        freeBoardRepository.save(freeBoard);

        return freeBoard;
    }

    @Override
    public List<FreeBoardListResponse> list() {
        List<FreeBoard> freeBoardList = freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "freeBoardId"));
        List<FreeBoardListResponse> freeBoardListResponseList = new ArrayList<>();

        for(FreeBoard freeBoard: freeBoardList) {
            FreeBoardListResponse freeBoardListResponse = new FreeBoardListResponse(freeBoard);
            freeBoardListResponseList.add(freeBoardListResponse);
        }

        return freeBoardListResponseList;
    }

    @Override
    public FreeBoard read(Long freeBoardId) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findByFreeBoardId(freeBoardId);

        if(maybeFreeBoard.isEmpty()) {
            System.out.println("freeBoardId 에 해당하는 게시물이 존재하지 않습니다.");
            return null;
        }

        return maybeFreeBoard.get();
    }

    @Override
    public void modify(FreeBoardModifyRequest freeBoardModifyRequest) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findByFreeBoardId(freeBoardModifyRequest.getFreeBoardId());

        if(maybeFreeBoard.isEmpty()) {
            System.out.println("freeBoardId 에 해당하는 게시물이 존재하지 않습니다.");
            return;
        }

        FreeBoard freeBoard = maybeFreeBoard.get();
        freeBoard.setTitle(freeBoardModifyRequest.getTitle());
        freeBoard.setContent(freeBoardModifyRequest.getContent());

        freeBoardRepository.save(freeBoard);
    }


}
