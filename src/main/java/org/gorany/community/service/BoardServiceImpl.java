package org.gorany.community.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.dto.UploadResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.Member;
import org.gorany.community.entity.UploadResult;
import org.gorany.community.repository.BoardRepository;
import org.gorany.community.repository.ReplyRepository;
import org.gorany.community.repository.UploadResultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @NonNull
    private BoardRepository boardRepository;

    @NonNull
    private ReplyRepository replyRepository;

    @NonNull
    private UploadResultRepository uploadRepository;

    @Override
    @Transactional
    public int register(BoardDTO boardDTO) {

        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        Board board = (Board) entityMap.get("board");
        List<UploadResult> uploadList = (List<UploadResult>) entityMap.get("uploadList");

        log.info("@BoardService, register");
        log.info(boardDTO);
        log.info("---------------------------------------");
        log.info(board);
        log.info(uploadList);

        boardRepository.save(board);

        if(uploadList != null && uploadList.size() > 0)
        uploadList.forEach(upload -> {
            uploadRepository.save(upload);
        });

        return board.getBno();
    }

    @Override
    public BoardDTO read(int bno) {

        Object obj = boardRepository.readBoard(bno);
        Object[] arr = (Object[]) obj;
        log.info("@BoardService, read : " + Arrays.toString(arr));

        BoardDTO dto = entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);

        List<UploadResultDTO> list = uploadRepository.getUploadResultByBoard(Board.builder().bno(bno).build()).stream().map(entity -> {
            UploadResultDTO uploadResultDTO = UploadResultDTO.builder()
                    .fileName(entity.getFileName())
                    .uuid(entity.getUuid())
                    .folderPath(entity.getFolderPath())
                    .image(entity.isImage())
                    .bno(entity.getBoard().getBno())
                    .build();

            return uploadResultDTO;
        }).collect(Collectors.toList());

        dto.setUploadList(list);

        return dto;
    }

    @Override
    @Transactional
    public void remove(int bno) {
        log.info("@BoardService, remove : " + bno);

        uploadRepository.deleteByBno(bno);
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    @Transactional
    public void modify(BoardDTO boardDTO) {

        log.info("@BoardService, modify " + boardDTO);

        //Upload File의 수정 (모두 삭제(DB 상에서만 삭제이고, 실제 로컬 저장소에는 그대로 있음) -> 다시 추가)
        uploadRepository.deleteByBno(boardDTO.getBno());

        List<UploadResult> uploadList = (List<UploadResult>) dtoToEntity(boardDTO).get("uploadList");
        if(uploadList != null && uploadList.size() > 0)
            uploadList.forEach(upload -> {
                uploadRepository.save(upload);
            });

        //Board의 내용만 수정
        Board board = boardRepository.getOne(boardDTO.getBno());

        if(board != null){
            board.changeAnonymous(boardDTO.isAnonymous());
            board.changeContent(boardDTO.getContent());
            board.changeTitle(board.getTitle());

            boardRepository.save(board);
        }
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info("@BoardService, PageRequestDTO : " + pageRequestDTO);
        Function<Object[], BoardDTO> function = (entity -> entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]));

        //Page<Object[]> result = boardRepository.getListWithPaging(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno").descending()));
        result.stream().forEach(item->log.info(Arrays.toString(item)));

        return new PageResultDTO<>(result, function);
    }
}
