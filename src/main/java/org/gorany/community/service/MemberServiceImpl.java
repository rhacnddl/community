package org.gorany.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.entity.Member;
import org.gorany.community.entity.MemberRole;
import org.gorany.community.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public int signup(String account, String password, String name) {

        Optional<Member> check = repository.findByAccount(account);

        if(check.isPresent()) return 0;

        Member member = Member.builder()
                .account(account)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();

        member.setRole(MemberRole.USER);

        log.info("@MemberService, " + member);

        repository.save(member);
        return 1;
    }
}