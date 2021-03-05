package org.gorany.community.repository;

import org.gorany.community.entity.Board;
import org.gorany.community.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository repo;

    @Test
    public void insertBoardTest(){

        IntStream.range(1, 101).forEach(i->{

            int idx = i;

            Board board = Board.builder()
                    .title(i+" 번 게시글")
                    .content(i + " 번 게시글의 내용입니다.")
                    .writer(Member.builder().account("gorany" + idx).build())
                    .anonymous(false)
                    .build();

            repo.save(board);
        });
    }
    /*@Test
    public void getListWithPagingTest(){

        Pageable pageable = PageRequest.of(9, 15, Sort.by("bno").descending());

        Page<Object[]> temp = repo.getListWithPaging(pageable);

        temp.stream().forEach(obj -> {
            System.out.println(Arrays.toString(obj));
        });
    }*/
    @Test
    public void readBoardTest(){

        int bno = 3;

        Object temp = repo.readBoard(bno);

        Object[] res = (Object[]) temp;

        System.out.println(Arrays.toString(res));
    }
    /*@Test
    public void SearchBoardRepoTest(){

        //repo.search1();
        Page<Object[]> result = repo.searchPage("t", "글", PageRequest.of(0, 10, Sort.by("bno").descending()));

        result.get().forEach(System.out::println);
    }*/
}