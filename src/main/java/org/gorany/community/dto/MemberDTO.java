package org.gorany.community.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.entity.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@ToString
@Log4j2
public class MemberDTO extends User {

    private String account;
    private String password;
    private String name;
    private String profile;
    private int role;

    private ProfileDTO profileDTO;

    public MemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.account = username;
        this.password = password;
    }

}
