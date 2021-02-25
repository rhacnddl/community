package org.gorany.community.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.ReplyDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Reply;
import org.gorany.community.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    @NonNull
    private ReplyRepository repository;

    @Override
    public int register(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);
        repository.save(reply);
        log.info(replyDTO);
        log.info("#ReplyService, register : " + reply);

        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(int bno) {

        List<Reply> list = repository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        log.info("#ReplyService, getList");
        list.forEach(r->log.info(r));

        return list.stream().map(r -> entityToDTO(r)).collect(Collectors.toList());
}

    @Override
    public void modify(ReplyDTO replyDTO) {

        log.info("#ReplyService, modify " + replyDTO);

        Reply reply = repository.getOne(replyDTO.getRno());
        reply.changeAnonymous(replyDTO.isAnonymous());
        reply.changeText(replyDTO.getText());

        repository.save(reply);
    }

    @Override
    public void remove(int rno) {

        log.info("#ReplyService, remove " + rno);
        repository.deleteById(rno);
    }
}
