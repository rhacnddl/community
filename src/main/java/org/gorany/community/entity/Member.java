package org.gorany.community.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}
