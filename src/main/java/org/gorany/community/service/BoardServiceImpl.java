package org.gorany.community.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Member;
import org.gorany.community.repository.BoardRepository;
import org.gorany.community.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @NonNull
    private BoardRepository boardRepository;

    @NonNull
    private ReplyRepository replyRepository;

    @Override
    public int register(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);
        log.info("@BoardService, register" + boardDTO);
        log.info(board);

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public BoardDTO read(int bno) {

        Object obj = boardRepository.readBoard(bno);
        Object[] arr = (Object[]) obj;
        log.info("@BoardService, read : " + Arrays.toString(arr));

        return entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Override
    @Transactional
    public void remove(int bno) {
        log.info("@BoardService, remove : " + bno);

        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    @Transactional
    public void modify(BoardDTO boardDTO) {

        log.info("@BoardService, modify " + boardDTO);

        Board board = boardRepository.getOne(boardDTO.getBno());

        if(board != null){
            board.changeAnonymous(boardDTO.isAnonymous());
            board.changeContent(boardDTO.getContent());
            board.changeTitle(board.getTitle());

            boardRepository.save(board);
        }
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info("@BoardService, PageRequestDTO : " + pageRequestDTO);
        Function<Object[], BoardDTO> function = (entity -> entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]));

        //Page<Object[]> result = boardRepository.getListWithPaging(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno").descending()));
        result.stream().forEach(item->log.info(item));

        return new PageResultDTO<>(result, function);
    }
}
