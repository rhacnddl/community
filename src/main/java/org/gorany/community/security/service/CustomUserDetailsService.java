package org.gorany.community.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.entity.Member;
import org.gorany.community.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("CustomUserDetailsService, loadByUsername : " + username);

        Optional<Member> result = repository.findByAccount(username);

        if(result.isEmpty())
            throw new UsernameNotFoundException("Check your account or password");

        Member member = result.get();

        log.info("------------------------------------------------------");
        log.info(member);

        MemberDTO memberDTO = new MemberDTO(member.getAccount(),
                member.getPassword(),
                member.getRoleSet().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        .collect(Collectors.toSet()));

        memberDTO.setName(member.getName());
        memberDTO.setProfile(member.getProfile());

        log.info(memberDTO);

        return memberDTO;
    }
}
