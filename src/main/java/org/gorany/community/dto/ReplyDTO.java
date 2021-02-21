package org.gorany.community.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private int rno, bno;

    private String text, replyer;
    private boolean anonymous;
    private LocalDateTime regDate, modDate;
}
