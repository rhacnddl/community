package org.gorany.community.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService service;

    @Test
    public void getListTest(){

        service.getList().stream().forEach(System.out::println);
    }
}
