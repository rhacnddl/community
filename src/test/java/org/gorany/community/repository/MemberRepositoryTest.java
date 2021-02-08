package org.gorany.community.repository;

import org.gorany.community.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository repo;

    @Test
    public void insertMemberTest(){

        IntStream.rangeClosed(1, 30).forEach(i->{
            Member member = Member.builder()
                    .account("gorany"+i)
                    .name(i+"번째 고라니")
                    .password("12345")
                    .build();

            repo.save(member);
        });
    }
}
