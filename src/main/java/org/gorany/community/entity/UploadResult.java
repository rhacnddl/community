package org.gorany.community.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Builder
public class UploadResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;

    private String fileName;
    private String uuid;
    private String folderPath;
    private boolean image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
