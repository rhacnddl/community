package org.gorany.community.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDTO {

    private String account;
    private String password;
    private String name;
    private String profile;

}
