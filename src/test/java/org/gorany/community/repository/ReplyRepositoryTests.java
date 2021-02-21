package org.gorany.community.repository;

import org.gorany.community.entity.Board;
import org.gorany.community.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository repository;

    /*@Test
    public void insertTest(){

        IntStream.rangeClosed(1, 100).forEach(i->{
            int bno = (int)(Math.random() * 100) + 3;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply : ..." + i)
                    .replyer("guest")
                    .board(board)
                    .build();

            repository.save(reply);
        });
    }*/
/*    @Test
    @Transactional
    public void readReply1(){
        Optional<Reply> result = repository.findById(101);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }*/
    @Test
    public void getRepliesTest(){

        List<Reply> result = repository.getRepliesByBoardOrderByRno(Board.builder().bno(3).build());
        result.forEach(System.out::println);
    }
}
