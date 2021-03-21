package org.gorany.community.service;

import org.gorany.community.dto.ProfileDTO;
import org.gorany.community.dto.ReplyDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Reply;

import java.util.List;

public interface ReplyService {

    int register(ReplyDTO replyDTO);
    List<Object[]> getList(int bno);
    void modify(ReplyDTO replyDTO);
    void remove(int rno);

    default ReplyDTO entityToDTO(Reply entity){

        return ReplyDTO.builder()
                .rno(entity.getRno())
                .text(entity.getText())
                .replyer(entity.getReplyer())
                .anonymous(entity.isAnonymous())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }
    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder().bno(dto.getBno()).build();

        return Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .anonymous(dto.isAnonymous())
                .build();
    }
}
