package org.gorany.community.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
@Log4j2
public class MemberDTO extends User {

    private String account;
    private String password;
    private String name;
    private String profile;

    public MemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.account = username;
        this.password = password;
    }
}
