package org.gorany.community.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, ENTITY> {

    private int page, size;
    private int start, end;

    private List<DTO> dtoList;

    public PageResultDTO(Page<ENTITY> result, Function<ENTITY, DTO> function){

        dtoList = result.stream().map(function).collect(Collectors.toList());
    }
}
