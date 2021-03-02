package org.gorany.community.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        String pw = "12345";
        String enpw = passwordEncoder.encode(pw);

        System.out.println(enpw);

        boolean match = passwordEncoder.matches(pw, enpw);
        System.out.println(match);
    }
}