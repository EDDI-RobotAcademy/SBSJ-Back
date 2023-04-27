package com.example.sbsj_process.board.service.freeBoardService;

import com.example.sbsj_process.board.entity.free.FreeBoard;
import com.example.sbsj_process.board.repository.freeRepository.FreeBoardRepository;
import com.example.sbsj_process.board.service.request.FreeBoardModifyRequest;
import com.example.sbsj_process.board.service.request.FreeBoardRegisterRequest;
import com.example.sbsj_process.board.service.response.FreeBoardListResponse;
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
public class FreeBoardServiceImpl implements FreeBoardService {

    final FreeBoardRepository freeBoardRepository;

    @Override
    public FreeBoard register(FreeBoardRegisterRequest freeBoardRegisterRequest) {
        FreeBoard freeBoard = freeBoardRegisterRequest.toFreeBoard();
        freeBoardRepository.save(freeBoard);

        return freeBoard;
    }

    @Override
    public List<FreeBoardListResponse> list(int startIndex, int endIndex) {
        List<FreeBoardListResponse> freeBoardListResponseList = freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "freeBoardId"))
                .stream()
                .map(FreeBoardListResponse::new)
                .collect(Collectors.toList());
        int size = freeBoardListResponseList.size();
        if (size >= endIndex) {
            return freeBoardListResponseList.subList(startIndex, endIndex);
        } else if (size >= startIndex) {
            return freeBoardListResponseList.subList(startIndex, size);
        } else {
            log.info("index out of bound");
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
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

    @Override
    public void remove(Long freeBoardId) {
        Optional<FreeBoard> maybeFreeBoard = freeBoardRepository.findByFreeBoardId(freeBoardId);

        if(maybeFreeBoard.isEmpty()) {
            System.out.println("freeBoardId 에 해당하는 게시물이 존재하지 않습니다.");
            return;
        }

        System.out.println("서비스에서 보는 삭제: "+ maybeFreeBoard.get());

        freeBoardRepository.deleteByFreeBoardId(freeBoardId);
    }

}
