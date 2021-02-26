package org.gorany.community.service;

import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.dto.UploadResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Member;
import org.gorany.community.entity.UploadResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {

    int register(BoardDTO boardDTO);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    BoardDTO read(int bno);
    void remove(int bno);
    void modify(BoardDTO boardDTO);

    default BoardDTO entityToDto(Board board, Member member, Long replyCnt){

        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(member.getAccount())
                .anonymous(board.isAnonymous())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .replyCnt(replyCnt.intValue())
                .build();

        return dto;
    }
    default Map<String, Object> dtoToEntity(BoardDTO boardDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Member writer = Member.builder().account(boardDTO.getWriter()).build();
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(writer)
                .anonymous(boardDTO.isAnonymous())
                .build();

        entityMap.put("board", board);

        List<UploadResultDTO> uploadDTOList = boardDTO.getUploadList();

        if(uploadDTOList != null && uploadDTOList.size() > 0){
            List<UploadResult> uploadList = uploadDTOList.stream().map(uploadDTO -> {
                    UploadResult upload = UploadResult.builder()
                            .fileName(uploadDTO.getFileName())
                            .uuid(uploadDTO.getUuid())
                            .folderPath(uploadDTO.getFolderPath())
                            .board(board)
                            .image(uploadDTO.isImage())
                            .build();

                    return upload;
            }).collect(Collectors.toList());

            entityMap.put("uploadList", uploadList);
        }
        return entityMap;
    }
    /*default Board dtoToEntity(BoardDTO boardDTO){

        Member writer = Member.builder().account(boardDTO.getWriter()).build();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(writer)
                .anonymous(boardDTO.isAnonymous())
                .build();

        return board;
    }*/

}
