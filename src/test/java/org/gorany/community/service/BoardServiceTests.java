package org.gorany.community.service;

import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    /*@Test
    public void getListTest(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = service.getList(pageRequestDTO);

        for(BoardDTO dto : result.getDtoList()){
            System.out.println(dto);
        }
    }*/
    /*@Test
    public void readBoardTest(){
        int bno = 10;
        BoardDTO dto = service.read(bno);

        System.out.println(dto);
    }*/
    /*@Test
    public void registerTest(){

        BoardDTO dto = BoardDTO.builder()
                .content("Test .... content")
                .title("Test .... title")
                .writer("gorany1")
                .build();

        service.register(dto);
    }*/
    @Test
    public void removeTest(){
        int bno = 4;

        service.remove(bno);
    }
}
