package org.gorany.community.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.ProfileDTO;
import org.gorany.community.dto.ReplyDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Profile;
import org.gorany.community.entity.Reply;
import org.gorany.community.repository.ProfileRepository;
import org.gorany.community.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    @NonNull
    private ReplyRepository repository;

    @NonNull
    private ProfileRepository profileRepository;

    @Override
    public int register(ReplyDTO replyDTO) {

        Reply reply = dtoToEntity(replyDTO);
        repository.save(reply);
        log.info(replyDTO);
        log.info("#ReplyService, register : " + reply);

        return reply.getRno();
    }

    @Override
    public List<Object[]> getList(int bno) {

        List<Reply> list = repository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        log.info("#ReplyService, getList");
        list.forEach(r->log.info(r));

        return list.stream().map(r -> {
            Object[] obj = new Object[2];
            obj[0] = (ReplyDTO) entityToDTO(r);
            obj[1] = null;
            Optional<Profile> tmp = profileRepository.findByAccount(r.getReplyer());

            if(tmp.isPresent()) {
                Profile profile = tmp.get();
                ProfileDTO profileDTO = new ProfileDTO(profile.getUuid(), profile.getFileName(), profile.getPath(), r.getReplyer());
                obj[1] = profileDTO;
            }

            return obj;
        }).collect(Collectors.toList());
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
