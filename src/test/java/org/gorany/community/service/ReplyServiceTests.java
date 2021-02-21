package org.gorany.community.service;

import org.gorany.community.dto.ReplyDTO;
import org.gorany.community.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;

    /*@Test
    public void getListTest(){

        List<ReplyDTO> list = service.getList(3);

        list.forEach(System.out::println);
    }*/
/*    @Test
    public void registerTest(){

        ReplyDTO dto = ReplyDTO.builder()
                .text("테스트 댓글 입니다.")
                .replyer("gorany1")
                .anonymous(true)
                .bno(3)
                .build();

        System.out.println(service.register(dto));
    }*/
    @Test
    public void modifyTest(){

        int rno = 150;

        ReplyDTO dto = ReplyDTO.builder()
                .text("수정된 테스트 댓글 입니다.")
                .replyer("gorany1")
                .bno(3)
                .rno(rno)
                .build();

        service.modify(dto);
    }
}