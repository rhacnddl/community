package org.gorany.community.repository;

import org.gorany.community.entity.Member;
import org.gorany.community.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Test
    public void insertTest(){

        IntStream.rangeClosed(50, 100).forEach(i->{
            Member member = Member.builder()
                    .account("gorany" + i)
                    .password(passwordEncoder.encode("12345"))
                    .name(i+"번째 고라니")
                    .build();

            if(i >= 90) member.setRole(MemberRole.ADMIN);
            else member.setRole(MemberRole.USER);

            repo.save(member);
        });
    }*/
    /*@Test
    public void insertMemberTest(){

        IntStream.rangeClosed(1, 30).forEach(i->{
            Member member = Member.builder()
                    .account("gorany"+i)
                    .name(i+"번째 고라니")
                    .password("12345")
                    .build();

            repo.save(member);
        });
    }*/

    /*@Test
    public void insertTest(){

        IntStream.rangeClosed(1, 100).forEach(i->{
            Member member = Member.builder()
                    .account("gorany" + i)
                    .name(i+"번 고라니")
                    .password(passwordEncoder.encode("12345"))
                    .build();

            if(i < 11) member.setRole(MemberRole.ADMIN);
            else member.setRole(MemberRole.USER);

            repo.save(member);
        });
    }*/

    /*@Test
    public void getMember(){

        Optional<Member> result = repo.findByAccount("gorany1");
        Member m = result.get();

        System.out.println(m.toString());
    }*/
    @Test
    public void getMemberListTest(){

        List<Member> list = repo.getMemberList();

        list.stream().forEach(System.out::println);
    }
}