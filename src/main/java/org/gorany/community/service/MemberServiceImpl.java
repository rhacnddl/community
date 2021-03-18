package org.gorany.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.dto.ProfileDTO;
import org.gorany.community.entity.Member;
import org.gorany.community.entity.MemberRole;
import org.gorany.community.entity.Profile;
import org.gorany.community.repository.MemberRepository;
import org.gorany.community.repository.ProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ProfileRepository profileRepository;
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

    @Override
    public List<MemberDTO> getList() {

        List<MemberDTO> list = repository.getMemberList().stream()
                .map(member -> entityToDTO(member))
                .collect(Collectors.toList());

        log.info("#MemberService, getList");
        log.info(list);

        return list;
    }

    @Override
    public void changeRole(String account, int role) {

        MemberRole memberRole = null;

        if(role == 1)
            memberRole = MemberRole.ADMIN;
        else
            memberRole = MemberRole.USER;

        Optional<Member> tmp = repository.findByAccount(account);

        Member member = tmp.get();
        member.setRole(memberRole);

        repository.save(member);
    }

    @Override
    @Transactional
    public void modify(String account, String name, String profile, ProfileDTO profileDTO) {

        log.info("@MemberService, modify");

        Optional<Member> memberOptional = repository.findByAccount(account);

        if(memberOptional.isEmpty()) return;

        Member member = memberOptional.get();
        member.changeProfile(profile);
        member.changeName(name);
        //member.changePassword(passwordEncoder.encode(memberDTO.getPassword())); //비밀번호 재설정은 아직

        if(profileDTO != null) {
            profileRepository.deleteByAccount(account);
            Profile prof = Profile.builder()
                    .uuid(profileDTO.getUuid())
                    .fileName(profileDTO.getFileName())
                    .path(profileDTO.getPath())
                    .member(member)
                    .build();

            log.info(prof);
            profileRepository.save(prof);
        }

        log.info(member);
        repository.save(member);
    }

    @Override
    public MemberDTO get(String account) {

        log.info("@MemberService, get(" + account + ")");

        Optional<Member> optional = repository.findByAccount(account);

        if (optional.isEmpty()) return null;

        Member member = optional.get();

        return entityToDTO(member);
    }
}
