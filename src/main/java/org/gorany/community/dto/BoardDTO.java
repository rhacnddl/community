package org.gorany.community.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardDTO {

    private int bno;

    private String title;
    private String content;
    private String writer;
    private int replyCnt;

    private boolean anonymous;

    private LocalDateTime regDate, modDate;
}
