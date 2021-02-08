package org.gorany.community.service;

import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Member;

public interface BoardService {

    int register(BoardDTO boardDTO);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    BoardDTO read(int bno);
    void remove(int bno);
    void modify(BoardDTO boardDTO);

    default BoardDTO entityToDto(Board board, Member member){

        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(member.getAccount())
                .anonymous(board.isAnonymous())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        return dto;
    }
    default Board dtoToEntity(BoardDTO boardDTO){

        Member writer = Member.builder().account(boardDTO.getWriter()).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(writer)
                .anonymous(boardDTO.isAnonymous())
                .build();

        return board;
    }
}
