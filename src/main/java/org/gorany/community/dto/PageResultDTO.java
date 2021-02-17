package org.gorany.community.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, ENTITY> {

    private int page, size;
    private int start, end;
    private int total;
    private boolean prev, next;

    private List<Integer> pageList;
    private List<DTO> dtoList;

    public PageResultDTO(Page<ENTITY> result, Function<ENTITY, DTO> function){

        dtoList = result.stream().map(function).collect(Collectors.toList());
        total = result.getTotalPages();
        makePageList(result.getPageable());
    }
    public void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;
        prev = start > 1;
        end = total > tempEnd? tempEnd:total;
        next = total > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
