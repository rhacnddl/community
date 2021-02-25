package org.gorany.community.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"board"})
public class Reply extends DateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    private String text;
    private String replyer;
    private boolean anonymous;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public void changeText(String text){
        this.text = text;
    }
    public void changeAnonymous(boolean anonymous){ this.anonymous = anonymous;}
}
