package org.gorany.community.service;

import org.gorany.community.dto.MemberDTO;
import org.gorany.community.dto.ProfileDTO;
import org.gorany.community.entity.Member;
import org.gorany.community.entity.MemberRole;
import org.gorany.community.entity.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public interface MemberService {

    int signup(String account, String password, String name);
    List<MemberDTO> getList();
    void changeRole(String account, int role);
    void modify(String account, String name, String profile, ProfileDTO profileDTO);
    MemberDTO get(String account);

    default MemberDTO entityToDTO(Member member, Profile profileImage){

        MemberDTO memberDTO = new MemberDTO(member.getAccount(),
                member.getPassword(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toSet()));

        for(MemberRole role : member.getRoleSet())
            if (role == MemberRole.ADMIN)
                memberDTO.setRole(1);
            else if(role == MemberRole.USER)
                memberDTO.setRole(0);
            else
                memberDTO.setRole(-1);

        if(profileImage != null){
            ProfileDTO profileDTO = new ProfileDTO(profileImage.getUuid(), profileImage.getFileName(), profileImage.getPath(), member.getAccount());
            memberDTO.setProfileDTO(profileDTO);
        }
        memberDTO.setName(member.getName());
        memberDTO.setProfile(member.getProfile());

        return memberDTO;
    }
}
