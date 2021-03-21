package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.ReplyDTO;
import org.gorany.community.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/replies")
public class ReplyController {

    private final ReplyService service;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object[]>> getList(@PathVariable("bno") int bno){

        log.info("@ReplyController, getList " + bno);

        List<Object[]> list = service.getList(bno);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping(value = "/")
    public ResponseEntity<Integer> register(@RequestBody ReplyDTO dto){
        log.info("@ReplyController, register " + dto);

        int rno = service.register(dto);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") int rno){

        log.info("@ReplyController, remove : " + rno);
        service.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping(value = "/{rno}")
    public ResponseEntity<String> modify(@PathVariable("rno") int rno, @RequestBody ReplyDTO replyDTO){

        log.info("@ReplyController, modify : " + replyDTO);
        service.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
