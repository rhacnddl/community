package org.gorany.community.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString(exclude = "member")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    private String uuid;

    private String fileName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
