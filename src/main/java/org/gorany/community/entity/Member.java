package org.gorany.community.entity;

import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member extends DateEntity{

    @Id
    private String account;

    private String name;
    private String password;
    private String profile;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void setRole(MemberRole role){
        roleSet.clear();
        roleSet.add(role);
    }

    public void changeName(String name){
        this.name = name;
    }
    public void changePassword(String password){
        this.password = password;
    }
    public void changeProfile(String profile){
        this.profile = profile;
    }
}
