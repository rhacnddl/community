package org.gorany.community.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@ToString(exclude = "writer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    private boolean anonymous;

    public void changeAnonymous(boolean anonymous){
        this.anonymous = anonymous;
    }
    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }
}
