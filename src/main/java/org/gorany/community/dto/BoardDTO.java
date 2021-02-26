package org.gorany.community.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private List<UploadResultDTO> uploadList = new ArrayList<>();

    private int replyCnt;

    private boolean anonymous;

    private LocalDateTime regDate, modDate;
}
